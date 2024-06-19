package org.teamvoided.nullium.data.tags

import net.minecraft.registry.RegistryKeys
import org.teamvoided.nullium.Nullium.id
import org.teamvoided.nullium.util.tag

object NulliumFluidTags {
    @JvmField
    val CANE_HYDRATION = create("cane_hydration")


    private fun create(id: String) = RegistryKeys.FLUID.tag(id(id))
}