package org.teamvoided.nullium.module

import net.minecraft.block.ComposterBlock
import org.teamvoided.nullium.config.NulConfigManager
import org.teamvoided.nullium.util.isAir
import org.teamvoided.nullium.util.item

object Compostable {
    fun init() {
        val cfg = NulConfigManager.main.data.stopping

        if (cfg.enableCompostable) {
            val comparable = NulConfigManager.compostable.data
            comparable.compostEntries.forEach { (id, layerChance) ->
                id.item().let {
                    if (!it.isAir()) ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(it, layerChance)
                }
            }
            comparable.entriesToRemove.forEach { id ->
                id.item().let {
                    if (!it.isAir()) ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.removeFloat(it)
                }
            }
        }
    }
}