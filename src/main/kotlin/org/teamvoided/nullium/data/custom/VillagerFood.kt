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
    fun getAmount(stack: ItemStack) = if (stack.isIn(items)) hungerAmount else null
    fun canEat(stack: ItemStack) = stack.isIn(items) && hungerAmount > 0

    companion object {
        const val ENABLED = true

        val CODEC: Codec<VillagerFood> = RecordCodecBuilder.create<VillagerFood> { instance ->
            instance.group(
                RegistryCodecs.homogeneousList<Item>(RegistryKeys.ITEM).fieldOf("items").forGetter { it.items },
                Codecs.NONNEGATIVE_INT.fieldOf("hunger_amount").forGetter { it.hungerAmount },
            ).apply(instance, ::VillagerFood)
        }

        @JvmStatic
        fun canEatFood(original: Integer?, stack: ItemStack, world: World): Integer? {
            var amount: Int?
            for (food in world.getVillagerFood()) {
                amount = food.getAmount(stack)
                if (amount != null) {
                    return if (amount > 0) amount as Integer else null
                }
            }
            if (Registries.ITEM.getId(stack.item).namespace != DEFAULT_NAMESPACE) return original
            return null
        }

        val visitedFoods = mutableMapOf<Item, VillagerFood>()

        @JvmStatic
        fun getFoodValues(inventory: SimpleInventory, world: World): Int {
            var totalFoodValue = 0
            base@ for (stack in inventory.stacks) {
                val item = stack.item
                val amount = visitedFoods[item]?.hungerAmount
                if (amount != null) {
                    totalFoodValue += (amount * stack.count)
                    continue
                }
                for (food in world.getVillagerFood()) {
                    val amount = food.getAmount(stack)
                    if (amount != null) {
                        visitedFoods[item] = food
                        totalFoodValue += (amount * stack.count)
                        continue@base
                    }
                }
                if (Registries.ITEM.getId(item).namespace != DEFAULT_NAMESPACE) {
                    val amount = ITEM_FOOD_VALUES[item]
                    if (amount != null) {
                        totalFoodValue += (amount * stack.count)
                    }
                }
            }
            return totalFoodValue
        }

        @JvmStatic
        fun canPickUp(stack: ItemStack, world: World): Boolean {
            for (food in world.getVillagerFood()) {
                if (food.canEat(stack)) return true
            }
            return false
        }
    }
}
