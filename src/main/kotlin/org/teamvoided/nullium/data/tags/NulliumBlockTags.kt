package org.teamvoided.nullium.data.tags

import net.minecraft.registry.RegistryKeys
import org.teamvoided.nullium.Nullium.id
import org.teamvoided.nullium.util.tag

object NulliumBlockTags {

    @JvmField
    val SUPPORT_SMALL_TOP = support("small/top")

    @JvmField
    val SUPPORT_SMALL_BOTTOM = support("small/bottom")

    @JvmField
    val PORTAL_BLOCKS = create("portal_blocks")

    @JvmField
    val MOSS_GROWS_UNDER = create("moss_grows_under")

    private fun create(id: String) = RegistryKeys.BLOCK.tag(id(id))
    private fun support(id: String) = create("support/$id")
}
