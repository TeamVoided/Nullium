package org.teamvoided.nullium.data

import net.minecraft.block.Block
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import org.teamvoided.nullium.Nullium.id

object NulliumBlockTags {

    @JvmField
    val CANE_SUPPORT = create("cane_support")
    @JvmField
    val CANE_HYDRATION = create("cane_hydration")


    private fun create(id: String): TagKey<Block> = TagKey.of(RegistryKeys.BLOCK, id(id))
}