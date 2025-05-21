package org.teamvoided.nullium.init

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.FeatureConfig
import net.minecraft.world.gen.feature.VegetationPatchFeatureConfig
import org.teamvoided.nullium.Nullium.id
import org.teamvoided.nullium.world.gen.feature.ExtendedVegetationPatchFeature

object NulFeatures {
    fun init() {}
    @Suppress("unused")
    val EXTENDED_VEGETATION_PATCH =
        register("extended_vegetation_patch", ExtendedVegetationPatchFeature(VegetationPatchFeatureConfig.CODEC))



    @Suppress("SameParameterValue")
    private fun <C : FeatureConfig, F : Feature<C>> register(name: String, feature: F): F =
        Registry.register(Registries.FEATURE, id(name), feature)
}
