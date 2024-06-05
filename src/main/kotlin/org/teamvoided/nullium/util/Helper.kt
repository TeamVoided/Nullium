package org.teamvoided.nullium.util

import com.mojang.brigadier.tree.CommandNode
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import org.teamvoided.nullium.Nullium.MODID

fun tText(text: String, vararg data: Any) = Text.translatable(text, *data)

fun ServerCommandSource.tFeedback(text: String, vararg args: Any, broadcast: Boolean = false) =
    this.sendFeedback({tText(text, *args)}, broadcast)

fun cmd(vararg data: String) = "command.$MODID.${data.joinToString(".")}"

fun <S> CommandNode<S>.childOf(node: CommandNode<S>): CommandNode<S> {
    node.addChild(this)
    return this
}
