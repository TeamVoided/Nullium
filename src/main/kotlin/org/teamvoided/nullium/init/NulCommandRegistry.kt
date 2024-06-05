package org.teamvoided.nullium.init

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.server.command.CommandManager.literal
import org.teamvoided.nullium.Nullium.MODID
import org.teamvoided.nullium.commands.ReloadConfigCommand

object NulCommandRegistry {
    fun init() = CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
        val nulliumNode = literal(MODID).build()
        dispatcher.root.addChild(nulliumNode)


        ReloadConfigCommand.register(nulliumNode)
    }
}
