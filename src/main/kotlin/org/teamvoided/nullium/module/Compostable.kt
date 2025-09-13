package org.teamvoided.nullium.module

import net.minecraft.block.ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE
import net.minecraft.item.Item
import org.teamvoided.nullium.Nullium.CONFIG
import org.teamvoided.nullium.util.isAir

object Compostable {
    val defaults = mutableMapOf<Item, Float>()
    fun init() {
        if (defaults.isNotEmpty()) {
            defaults.forEach { (item, layerChance) ->
                if (layerChance > 0) ITEM_TO_LEVEL_INCREASE_CHANCE.put(item, layerChance)
                else ITEM_TO_LEVEL_INCREASE_CHANCE.removeFloat(item)
            }
            defaults.clear()
        }
        if (!CONFIG.blocks.compostingChanges) return

        CONFIG.blocks.compostEntries.forEach { (item, layerChance) ->
            if (!item.isAir()) {
                val oldChance = ITEM_TO_LEVEL_INCREASE_CHANCE.put(item, layerChance)
                defaults[item] = oldChance
            }
        }

        CONFIG.blocks.entriesToRemove.forEach { item ->
            val oldChance = ITEM_TO_LEVEL_INCREASE_CHANCE.removeFloat(item)
            defaults.putIfAbsent(item, oldChance)
        }
    }
}