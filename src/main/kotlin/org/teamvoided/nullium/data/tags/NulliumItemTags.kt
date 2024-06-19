package org.teamvoided.nullium.data.tags

import net.minecraft.item.Item
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import org.teamvoided.nullium.Nullium.id

object NulliumItemTags {


    private fun create(id: String): TagKey<Item> = TagKey.of(RegistryKeys.ITEM, id(id))
}
