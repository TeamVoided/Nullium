package org.teamvoided.nullium.module

import net.minecraft.component.DataComponentTypes
import net.minecraft.item.ArmorMaterials
import net.minecraft.item.ItemStack
import net.minecraft.item.ToolMaterials
import net.minecraft.registry.tag.EnchantmentTags
import net.minecraft.world.World
import org.teamvoided.nullium.Nullium.CONFIG
import org.teamvoided.nullium.data.nullium.data.RepairData.Companion.dataFromConfig
import org.teamvoided.nullium.data.nullium.data.RepairData.Companion.getRepairData
import org.teamvoided.nullium.data.tags.NulliumMaterialTags
import org.teamvoided.nullium.util.set

object Blacksmith {

    fun repairOverrides() {
        if (!CONFIG.items.customRepairCosts) return
        ToolMaterials.WOOD.set(NulliumMaterialTags.REPAIR_WOOD)
        ToolMaterials.STONE.set(NulliumMaterialTags.REPAIR_STONE)
        ToolMaterials.GOLD.set(NulliumMaterialTags.REPAIR_GOLD)
        ToolMaterials.IRON.set(NulliumMaterialTags.REPAIR_IRON)
        ToolMaterials.DIAMOND.set(NulliumMaterialTags.REPAIR_DIAMOND)
        ToolMaterials.NETHERITE.set(NulliumMaterialTags.REPAIR_NETHERITE)

        ArmorMaterials.LEATHER.set(NulliumMaterialTags.REPAIR_LEATHER)
        ArmorMaterials.CHAIN.set(NulliumMaterialTags.REPAIR_CHAIN)
        ArmorMaterials.IRON.set(NulliumMaterialTags.REPAIR_IRON)
        ArmorMaterials.GOLD.set(NulliumMaterialTags.REPAIR_GOLD)
        ArmorMaterials.DIAMOND.set(NulliumMaterialTags.REPAIR_DIAMOND)
        ArmorMaterials.NETHERITE.set(NulliumMaterialTags.REPAIR_NETHERITE)
    }


    @JvmStatic
    fun calculateCost(world: World, stack: ItemStack): Int {
        val data = world.getRepairData(stack) ?: dataFromConfig()

        var enchantmentCosts = 0
        val enchantments = stack.get(DataComponentTypes.ENCHANTMENTS)?.enchantmentEntries
        if (enchantments != null && enchantments.isNotEmpty()) {
            val curses = enchantments.filter { it.key.isIn(EnchantmentTags.CURSE) }
            if (curses.isNotEmpty()) {
                enchantmentCosts -= curses.map { it.key.value().anvilCost * it.intValue }
                    .reduce { acc, i -> (acc + i) / 2 }
            }
            val notCurses = enchantments.filter { !it.key.isIn(EnchantmentTags.CURSE) }
            if (notCurses.isNotEmpty()) {
                enchantmentCosts += notCurses.map { it.key.value().anvilCost * it.intValue }
                    .reduce { acc, i -> (acc + i) / 2 }
            }
        }
        return data.baseCost + (enchantmentCosts * data.enchantmentMultiplier).toInt()
    }
}
