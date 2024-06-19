package org.teamvoided.nullium.util

import com.mojang.brigadier.tree.CommandNode
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributeInstance
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.loot.LootTable
import net.minecraft.loot.context.LootContextParameterSet
import net.minecraft.loot.context.LootContextType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.tag.TagKey
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.teamvoided.nullium.Nullium.MODID
import java.util.function.Supplier

fun tText(text: String, vararg data: Any) = Text.translatable(text, *data)

fun ServerCommandSource.tFeedback(text: String, vararg args: Any, broadcast: Boolean = false) =
    this.sendFeedback({tText(text, *args)}, broadcast)

fun cmd(vararg data: String) = "command.$MODID.${data.joinToString(".")}"

fun <S> CommandNode<S>.childOf(node: CommandNode<S>): CommandNode<S> {
    node.addChild(this)
    return this
}

// generic

fun <T> T.supply() = Supplier { this }


//mc

fun <T, Y: Registry<T>> RegistryKey<Y>.key(id: Identifier): RegistryKey<T> = RegistryKey.of(this, id)
fun <T, Y: Registry<T>> RegistryKey<Y>.tag(id: Identifier): TagKey<T> = TagKey.of(this, id)


fun Item.id() = Registries.ITEM.getId(this)
fun Item.isAir() = this == Items.AIR

fun ServerWorld.getLootTable(key: RegistryKey<LootTable>): LootTable = this.server.method_58576().getLootTable(key)
fun LootContextParameterSet(world: ServerWorld, type: LootContextType): LootContextParameterSet =
    LootContextParameterSet.Builder(world).build(type)

fun LivingEntity.attributeAsSet(): Set<EntityAttributeInstance> = this.attributes.method_60497()