package org.teamvoided.nullium.init

import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import org.teamvoided.nullium.Nullium.MODID
import org.teamvoided.nullium.commands.ReloadConfigCommand

object NulCommandRegistry {
    fun init() = CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
        val nulliumNode = literal(MODID).build()
        dispatcher.root.addChild(nulliumNode)


        ReloadConfigCommand.register(nulliumNode)

        if (FabricLoader.getInstance().isDevelopmentEnvironment)
            nulliumNode.addChild(literal("debug").executes(::debug).build())
    }


    fun debug(ctx: CommandContext<ServerCommandSource>): Int {
        val src = ctx.source ?: return -1
        val world = src.world ?: return -1
        val player = src.player ?: return -1

        player.sendSystemMessage(Text.literal("Hello! ${world.registryKey.value.path}"))
        return 0
    }
}
