package org.teamvoided.nullium.module

import arrow.core.left
import arrow.core.right
import net.minecraft.component.DataComponentTypes
import net.minecraft.item.ItemStack
import net.minecraft.registry.tag.EnchantmentTags
import org.teamvoided.nullium.config.NulConfigManager.blacksmith
import org.teamvoided.nullium.util.isAir
import org.teamvoided.nullium.util.item
import org.teamvoided.nullium.util.itemTag

object Blacksmith {
    @JvmStatic
    fun calculateCost(stack: ItemStack): Int {
        val cfg = blacksmith.data()
        val data = cfg.materialRepairCosts
            .mapKeys { p -> p.key.item().let { if (it.isAir()) p.key.itemTag().left() else it.right() } }
            .filter { p -> p.key.fold({ stack.isIn(it) }, { it == stack.item }) }
            .toList()
            .firstOrNull()
            ?.second ?: cfg.defaultRepairCost

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
