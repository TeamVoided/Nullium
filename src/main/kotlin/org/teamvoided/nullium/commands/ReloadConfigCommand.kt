package org.teamvoided.nullium.commands

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.server.command.ServerCommandSource
import org.teamvoided.nullium.config.NulConfigManager
import org.teamvoided.nullium.util.childOf
import org.teamvoided.nullium.util.cmd
import org.teamvoided.nullium.util.tFeedback

object ReloadConfigCommand {
    @Suppress("UNUSED_VARIABLE")
    fun register(root: LiteralCommandNode<ServerCommandSource>) {
        val reload = literal("reload")
            .executes(::reloadAll)
            .build()
            .childOf(root)
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
