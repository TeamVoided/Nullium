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
    @JvmField
    val WITHER_ROSE_PLACEABLE = create("wither_rose_placeable")
    @JvmField
    val SUPPORT_SMALL_TOP = create("support/small/top")
    @JvmField
    val SUPPORT_SMALL_BOTTOM = create("support/small/bottom")
    @JvmField
    val ENDERMAN_PLACEABLE = create("enderman_placeable")

    private fun create(id: String): TagKey<Block> = TagKey.of(RegistryKeys.BLOCK, id(id))
}
