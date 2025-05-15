package org.teamvoided.nullium.data.custom

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.entity.passive.VillagerEntity.ITEM_FOOD_VALUES
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.HolderSet
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryCodecs
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier.DEFAULT_NAMESPACE
import net.minecraft.util.dynamic.Codecs
import net.minecraft.world.World
import org.teamvoided.nullium.init.NulRegistryKeys.getVillagerFood

data class VillagerFood(val items: HolderSet<Item>, val hungerAmount: Int) {
    fun getAmount(stack: ItemStack) = if (contains(stack)) hungerAmount else null
    fun canEat(stack: ItemStack) = contains(stack) && hungerAmount > 0
    fun contains(stack: ItemStack) = stack.isIn(items)

    companion object {
        const val ENABLED = true
        const val FALLBACK = true

        val CODEC: Codec<VillagerFood> = RecordCodecBuilder.create<VillagerFood> { instance ->
            instance.group(
                RegistryCodecs.homogeneousList<Item>(RegistryKeys.ITEM).fieldOf("items").forGetter { it.items },
                Codecs.NONNEGATIVE_INT.fieldOf("hunger_amount").forGetter { it.hungerAmount },
            ).apply(instance, ::VillagerFood)
        }

        val visitedFoods = mutableMapOf<Item, VillagerFood>()

        fun World.getFood(stack: ItemStack): VillagerFood? {
            val food = visitedFoods[stack.item]
            if (food != null) return food

            for (food in this.getVillagerFood()) {
                if (stack.isIn(food.items)) {
                    visitedFoods[stack.item] = food
                    return food
                }
            }
            return null
        }

        @JvmStatic
        fun canEatFood(original: Integer?, stack: ItemStack, world: World): Integer? {
            val food = world.getFood(stack)
            if (food != null) {
                val amount = food.getAmount(stack)
                if (amount != null) {
                    return if (amount > 0) amount as Integer else null
                }
            }
            if (FALLBACK && Registries.ITEM.getId(stack.item).namespace != DEFAULT_NAMESPACE) return original
            return null
        }

        @JvmStatic
        fun getFoodValues(inventory: SimpleInventory, world: World): Int {
            var totalFoodValue = 0
            for (stack in inventory.stacks) {
                val amount = world.getFood(stack)?.hungerAmount
                if (amount != null) {
                    totalFoodValue += (amount * stack.count)
                    continue
                }
                if (FALLBACK && Registries.ITEM.getId(stack.item).namespace != DEFAULT_NAMESPACE) {
                    val amount = ITEM_FOOD_VALUES[stack.item]
                    if (amount != null) {
                        totalFoodValue += (amount * stack.count)
                    }
                }
            }
            return totalFoodValue
        }

        @JvmStatic
        fun canPickUp(stack: ItemStack, world: World): Boolean = world.getFood(stack)?.canEat(stack) == true

    }
}
