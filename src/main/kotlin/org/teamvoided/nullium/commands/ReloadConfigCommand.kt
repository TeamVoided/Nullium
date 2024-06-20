package org.teamvoided.nullium.commands

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import com.mojang.serialization.JsonOps
import net.minecraft.loot.LootTable
import net.minecraft.loot.LootTables
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.server.command.ServerCommandSource
import org.teamvoided.nullium.config.NulConfigManager
import org.teamvoided.nullium.util.childOf
import org.teamvoided.nullium.util.cmd
import org.teamvoided.nullium.util.getLootTable
import org.teamvoided.nullium.util.tFeedback

object ReloadConfigCommand {
    @Suppress("UNUSED_VARIABLE")
    fun register(root: LiteralCommandNode<ServerCommandSource>) {
        val reload = literal("reload")
            .executes(::reloadAll)
            .build()
            .childOf(root)
//        val test = literal("test")
//            .executes(::test)
//            .build()
//            .childOf(root)
    }

    private fun test(commandContext: CommandContext<ServerCommandSource>): Int {
        val src = commandContext.source
        val z = src.world.getLootTable(LootTables.PIGLIN_BARTERING_GAMEPLAY)
        val x = LootTable.field_50021.encodeStart(JsonOps.INSTANCE, z)
        if (x.isError) {
            src.tFeedback("Problēma! $x")
        } else {
            src.tFeedback(x.resultOrPartial().get().toString())
        }


        return 0
    }

    private fun reloadAll(it: CommandContext<ServerCommandSource>): Int {
        val failedModules = NulConfigManager.loadAll()
        return if (failedModules == 0) {
            it.source.tFeedback(cmd("reload", "success"))
            1
        } else {
            it.source.tFeedback(cmd("reload", "failed"), failedModules)
            0
        }
    }
}
