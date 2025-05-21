package org.teamvoided.nullium.data.world.gen

import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.world.gen.feature.ConfiguredFeature
import org.teamvoided.nullium.Nullium.id

object NulConfiguredFeatures {

    @JvmField
    val MOSS_PATCH_BONEMEAL = create("moss_patch_bonemeal")

    fun create(path: String): RegistryKey<ConfiguredFeature<*,*>> = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, id(path))
}