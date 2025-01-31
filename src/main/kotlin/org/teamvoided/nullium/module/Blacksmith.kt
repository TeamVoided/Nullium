package org.teamvoided.nullium.module

import net.minecraft.component.DataComponentTypes
import net.minecraft.item.ArmorMaterials
import net.minecraft.item.ItemStack
import net.minecraft.item.ToolMaterials
import net.minecraft.registry.tag.EnchantmentTags
import org.teamvoided.nullium.config.NulConfigManager.blacksmith
import org.teamvoided.nullium.config.data.IdentifierType
import org.teamvoided.nullium.data.tags.NulliumMaterialTags
import org.teamvoided.nullium.util.item
import org.teamvoided.nullium.util.itemTag
import org.teamvoided.nullium.util.set

object Blacksmith {

    fun repairOverrides() {
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
    fun calculateCost(stack: ItemStack): Int {
        val cfg = blacksmith.data()
        val data = cfg.materialRepairCosts
            .filter {
                when (it.type) {
                    IdentifierType.ITEM -> it.id.item() == stack.item
                    IdentifierType.TAG -> stack.isIn(it.id.itemTag())
                }
            }
            .toList()
            .firstOrNull()
            ?.data ?: cfg.defaultRepairCost

        var enchantmentCosts = 0
        val enchantments = stack.get(DataComponentTypes.ENCHANTMENTS)
        if (enchantments != null) {
            val curses = enchantments.enchantmentEntries.filter { it.key.isIn(EnchantmentTags.CURSE) }
            val notCurses = enchantments.enchantmentEntries.filter { !it.key.isIn(EnchantmentTags.CURSE) }
            if (curses.isNotEmpty()) {
                enchantmentCosts -= curses.map { it.key.value().anvilCost * it.intValue }
                    .reduce { acc, i -> (acc + i) / 2 }
            }
            if (notCurses.isNotEmpty()) {
                enchantmentCosts += notCurses.map { it.key.value().anvilCost * it.intValue }
                    .reduce { acc, i -> (acc + i) / 2 }
            }
        }
        return data.baseCost + (enchantmentCosts * data.enchantmentMultiplier).toInt()
    }
}
