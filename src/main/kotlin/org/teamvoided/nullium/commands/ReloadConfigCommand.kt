package org.teamvoided.nullium.commands

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import org.teamvoided.nullium.config.ConfigManager

object ReloadConfigCommand {
    fun init(root: LiteralCommandNode<ServerCommandSource>) {
        val reload = literal("reload").executes(::reloadAll).build()

        root.addChild(reload)
    }

    fun reloadAll(it: CommandContext<ServerCommandSource>): Int {
        val modulesLoaded = ConfigManager.loadAll()
        return if (modulesLoaded == 0) {
            it.source.feedback(Text.of("All modules successfully reloaded!"))
            1
        } else {
            it.source.feedback(Text.of("Failed to reload $modulesLoaded modules!"))
            0
        }
    }

    fun ServerCommandSource.feedback(text: Text, broadcastToOps: Boolean = false) {
        this.sendFeedback({ text }, broadcastToOps)
    }
}
