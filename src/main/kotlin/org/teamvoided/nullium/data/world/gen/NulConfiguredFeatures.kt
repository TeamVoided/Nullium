package org.teamvoided.nullium.data.world.gen

import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.world.gen.feature.ConfiguredFeature
import org.teamvoided.nullium.Nullium.id

object NulConfiguredFeatures {

    @JvmField
    val ENHANCED_MOSS_PATCH_BONEMEAL = create("enhanced_moss_patch_bonemeal")

    fun create(path: String): RegistryKey<ConfiguredFeature<*, *>> =
        RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, id(path))
}