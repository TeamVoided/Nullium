package org.teamvoided.nullium.util

import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier

fun Identifier.itemTag(): TagKey<Item> = TagKey.of(RegistryKeys.ITEM, this)
fun Identifier.item(): Item = Registries.ITEM.get(this)


