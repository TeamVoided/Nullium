package org.teamvoided.nullium.data.tags

import net.minecraft.registry.RegistryKeys
import org.teamvoided.nullium.Nullium.id
import org.teamvoided.nullium.util.tag

object NulliumBlockTags {

    @JvmField
    val CANE_HYDRATION = create("cane_hydration")

    @JvmField
    val ENDERMAN_PLACEABLE = create("enderman_placeable")

    @JvmField
    val SUPPORT_SMALL_TOP = support("small/top")
    @JvmField
    val SUPPORT_SMALL_BOTTOM = support("small/bottom")

    @JvmField
    val CANE_SUPPORT = support("cane")
    @JvmField
    val WITHER_ROSE_SUPPORT = support("wither_rose")
    @JvmField
    val CACTUS_SUPPORT = support("cactus")
    @JvmField
    val NETHER_WART_SUPPORT = support("nether_wart")

    @JvmField
    val NYLIUM_PLANTS_SUPPORT = support("nylium_plants")


    private fun create(id: String) = RegistryKeys.BLOCK.tag(id(id))
    private fun support(id: String) = create("support/$id")
}
