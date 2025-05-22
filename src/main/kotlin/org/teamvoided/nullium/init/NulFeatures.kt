package org.teamvoided.nullium.init

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.FeatureConfig
import org.teamvoided.nullium.Nullium.id
import org.teamvoided.nullium.world.gen.feature.EnhancedVegetationPatchFeature
import org.teamvoided.nullium.world.gen.feature.cfg.EnhancedVegetationPatchFeatureConfig

object NulFeatures {
    fun init() {}

    @Suppress("unused")
    val ENHANCED_VEGETATION_PATCH =
        register(
            "enhanced_vegetation_patch",
            EnhancedVegetationPatchFeature(EnhancedVegetationPatchFeatureConfig.CODEC)
        )


    @Suppress("SameParameterValue")
    private fun <C : FeatureConfig, F : Feature<C>> register(name: String, feature: F): F =
        Registry.register(Registries.FEATURE, id(name), feature)
}
