package org.teamvoided.nullium.module

import net.minecraft.component.DataComponentTypes
import net.minecraft.item.ItemStack
import net.minecraft.registry.tag.EnchantmentTags
import org.teamvoided.nullium.config.NulConfigManager.blacksmith
import org.teamvoided.nullium.config.data.IdentifierType
import org.teamvoided.nullium.util.item
import org.teamvoided.nullium.util.itemTag

object Blacksmith {
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
