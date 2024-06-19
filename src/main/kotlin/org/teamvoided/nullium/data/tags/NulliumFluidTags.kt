package org.teamvoided.nullium.data.tags

import net.minecraft.fluid.Fluid
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import org.teamvoided.nullium.Nullium.id

object NulliumFluidTags {
    @JvmField
    val CANE_HYDRATION = create("cane_hydration")


    private fun create(id: String): TagKey<Fluid> = TagKey.of(RegistryKeys.FLUID, id(id))
}