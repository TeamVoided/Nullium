package org.teamvoided.nullium.module

import net.minecraft.block.Blocks
import net.minecraft.entity.mob.EndermanEntity
import net.minecraft.item.BlockItem
import net.minecraft.loot.context.LootContextTypes
import net.minecraft.server.world.ServerWorld
import org.teamvoided.nullium.util.LootContextParameterSet
import org.teamvoided.nullium.util.getLootTable

object HolderMan {
    @JvmStatic
    fun getBlocks(world: ServerWorld, end: EndermanEntity) {
        val states = world.getLootTable(Blocks.GILDED_BLACKSTONE.lootTableId)
            .generateLoot(LootContextParameterSet(world, LootContextTypes.EMPTY))
            .mapNotNull {
                if (it.item is BlockItem) (it.item as BlockItem).block.defaultState
                else Blocks.AIR.defaultState
            }

        if (states.isNotEmpty()) {
            states.random().let {
                if (!it.isAir) end.carriedBlock = it
            }
        }
    }
}
