package org.teamvoided.nullium.world.gen.feature.cfg

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.block.Block
import net.minecraft.registry.Holder
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.math.VerticalSurfaceType
import net.minecraft.util.math.int_provider.IntProvider
import net.minecraft.world.gen.feature.PlacedFeature
import net.minecraft.world.gen.feature.VegetationPatchFeatureConfig
import net.minecraft.world.gen.stateprovider.BlockStateProvider

class EnhancedVegetationPatchFeatureConfig(
    val canGrowUnder: TagKey<Block>,
    replaceable: TagKey<Block>,
    groundState: BlockStateProvider,
    vegetationFeature: Holder<PlacedFeature>,
    surface: VerticalSurfaceType,
    depth: IntProvider,
    extraBottomBlockChance: Float,
    verticalRange: Int,
    vegetationChance: Float,
    horizontalRadius: IntProvider,
    extraEdgeColumnChance: Float,
) : VegetationPatchFeatureConfig(
    replaceable,
    groundState,
    vegetationFeature,
    surface,
    depth,
    extraBottomBlockChance,
    verticalRange,
    vegetationChance,
    horizontalRadius,
    extraEdgeColumnChance
) {

    companion object {
        val CODEC: Codec<EnhancedVegetationPatchFeatureConfig> =
            RecordCodecBuilder.create<EnhancedVegetationPatchFeatureConfig> { instance ->
                instance!!.group(
                    TagKey.createHashedCodec(RegistryKeys.BLOCK).fieldOf("can_grow_under")
                        .forGetter { it.canGrowUnder },
                    TagKey.createHashedCodec(RegistryKeys.BLOCK).fieldOf("replaceable").forGetter { it.replaceable },
                    BlockStateProvider.TYPE_CODEC.fieldOf("ground_state").forGetter { it.groundState },
                    PlacedFeature.REGISTRY_CODEC.fieldOf("vegetation_feature").forGetter { it.vegetationFeature },
                    VerticalSurfaceType.CODEC.fieldOf("surface").forGetter { it.surface },
                    IntProvider.method_35004(1, 128).fieldOf("depth").forGetter { it.depth },
                    Codec.floatRange(0.0f, 1.0f).fieldOf("extra_bottom_block_chance")
                        .forGetter { it.extraBottomBlockChance },
                    Codec.intRange(1, 256).fieldOf("vertical_range").forGetter { it.verticalRange },
                    Codec.floatRange(0.0f, 1.0f).fieldOf("vegetation_chance").forGetter { it.vegetationChance },
                    IntProvider.VALUE_CODEC.fieldOf("xz_radius").forGetter { it.horizontalRadius },
                    Codec.floatRange(0.0f, 1.0f).fieldOf("extra_edge_column_chance")
                        .forGetter { it.extraEdgeColumnChance }
                ).apply(instance, ::EnhancedVegetationPatchFeatureConfig)
            }
    }
}
