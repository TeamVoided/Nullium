package org.teamvoided.nullium.data.tags

import net.minecraft.registry.RegistryKeys
import org.teamvoided.nullium.Nullium.id
import org.teamvoided.nullium.util.tag

object NulliumBlockTags {
    @JvmField
    val MOSS_GROWS_UNDER = create("moss_grows_under")

    // Special Support
    @JvmField
    val SUPPORTS_SMALL_TOP = supports("small/top")

    @JvmField
    val SUPPORTS_SMALL_BOTTOM = supports("small/bottom")

    @JvmField
    val SUPPORTS_SMALL_SIDES = supports("small/sides")

    private fun create(id: String) = RegistryKeys.BLOCK.tag(id(id))
    private fun supports(id: String) = create("supports/$id")
}
