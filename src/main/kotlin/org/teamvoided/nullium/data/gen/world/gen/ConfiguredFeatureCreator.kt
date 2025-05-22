package org.teamvoided.nullium.data.gen.world.gen

import net.minecraft.block.Blocks
import net.minecraft.registry.BootstrapContext
import net.minecraft.registry.Holder
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.BlockTags
import net.minecraft.util.math.VerticalSurfaceType
import net.minecraft.util.math.int_provider.ConstantIntProvider
import net.minecraft.util.math.int_provider.UniformIntProvider
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.FeatureConfig
import net.minecraft.world.gen.feature.UndergroundConfiguredFeatures
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil
import net.minecraft.world.gen.stateprovider.BlockStateProvider
import org.teamvoided.nullium.data.tags.NulliumBlockTags
import org.teamvoided.nullium.data.world.gen.NulConfiguredFeatures
import org.teamvoided.nullium.init.NulFeatures
import org.teamvoided.nullium.world.gen.feature.cfg.EnhancedVegetationPatchFeatureConfig

typealias ConfFeature = ConfiguredFeature<*, *>

object ConfiguredFeatureCreator {
    fun bootstrap(c: BootstrapContext<ConfFeature>) {
        val lookup = c.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE)
        c.make(
            NulConfiguredFeatures.ENHANCED_MOSS_PATCH_BONEMEAL,
            NulFeatures.ENHANCED_VEGETATION_PATCH,
            EnhancedVegetationPatchFeatureConfig(
                NulliumBlockTags.MOSS_GROWS_UNDER,
                BlockTags.MOSS_REPLACEABLE,
                BlockStateProvider.of(Blocks.MOSS_BLOCK),
                PlacedFeatureUtil.placedInline(lookup.getHolderOrThrow(UndergroundConfiguredFeatures.MOSS_VEGETATION)),
                VerticalSurfaceType.FLOOR,
                ConstantIntProvider.create(1),
                0.0f, 5, 0.6f,
                UniformIntProvider.create(1, 2),
                0.75f
            )
        )
    }

    fun <FC : FeatureConfig, F : Feature<FC>> BootstrapContext<ConfFeature>.make(
        key: RegistryKey<ConfFeature>, feature: F, config: FC,
    ): Holder.Reference<ConfFeature> = this.register(key, ConfiguredFeature(feature, config))

}