package org.teamvoided.nullium.data.nullium.data

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.entity.passive.VillagerEntity.ITEM_FOOD_VALUES
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.*
import net.minecraft.util.Identifier.DEFAULT_NAMESPACE
import net.minecraft.util.dynamic.Codecs
import net.minecraft.world.World
import org.teamvoided.nullium.Nullium.CONFIG
import org.teamvoided.nullium.Nullium.MODID
import org.teamvoided.nullium.init.NulRegistryKeys.VILLAGER_FOOD

data class VillagerFood(val items: HolderSet<Item>, val hungerAmount: Int) {
    fun getAmount(stack: ItemStack) = if (contains(stack)) hungerAmount else null
    fun canEat(stack: ItemStack) = contains(stack) && hungerAmount > 0
    fun contains(stack: ItemStack) = stack.isIn(items)

    companion object {
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

            for (food in getVillagerFood()) {
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
            if (food == null) {
                return if (CONFIG.entities.moddedFoodFallback && stack.isNotMC()) original
                else null
            }
            val amount = food.getAmount(stack)
            if (amount != null) {
                return if (amount > 0) amount as Integer else null
            }
            return null
        }

        @JvmStatic
        fun getFoodValues(inventory: SimpleInventory, world: World): Int {
            var totalFoodValue = 0
            for (stack in inventory.stacks) {
                var amount = world.getFood(stack)?.hungerAmount
                if (amount == null) {
                    if (CONFIG.entities.moddedFoodFallback && stack.isNotMC()) {
                        amount = ITEM_FOOD_VALUES[stack.item]
                    } else continue
                }
                require(amount != null) { "[$MODID] Food Amount was \"null\", when it should not have been!" }
                totalFoodValue += (amount * stack.count)
            }
            return totalFoodValue
        }

        @JvmStatic
        fun canPickUp(stack: ItemStack, world: World, original: Boolean): Boolean {
            val food = world.getFood(stack)
            if (food != null) return food.canEat(stack)
            if (CONFIG.entities.moddedFoodFallback && stack.isNotMC()) {
                return ITEM_FOOD_VALUES.contains(stack.item)
            }
            return if (ITEM_FOOD_VALUES.contains(stack.item)) false else original
        }

        fun ItemStack.isNotMC() = Registries.ITEM.getId(this.item).namespace != DEFAULT_NAMESPACE
        fun World.getVillagerFood(): Registry<VillagerFood> = this.registryManager.get(VILLAGER_FOOD)
    }
}
