package org.teamvoided.nullium.world.gen.feature

import com.mojang.serialization.Codec
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.util.random.RandomGenerator
import net.minecraft.world.StructureWorldAccess
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.feature.VegetationPatchFeature
import net.minecraft.world.gen.feature.VegetationPatchFeatureConfig
import org.teamvoided.nullium.data.tags.NulliumBlockTags.MOSS_GROWS_UNDER
import java.util.function.Predicate

class ExtendedVegetationPatchFeature(codec: Codec<VegetationPatchFeatureConfig>) : VegetationPatchFeature(codec) {
    override fun placeGroundAndGetPositions(
        world: StructureWorldAccess, config: VegetationPatchFeatureConfig, random: RandomGenerator,
        pos: BlockPos, replaceable: Predicate<BlockState>, radiusX: Int, radiusZ: Int,
    ): MutableSet<BlockPos> {
        val mutable = pos.mutableCopy()
        val mutable2 = mutable.mutableCopy()
        val direction = config.surface.direction
        val direction2 = direction.opposite
        val set = HashSet<BlockPos>()
        for (i in -radiusX..radiusX) {
            val bl = i == -radiusX || i == radiusX
            for (j in -radiusZ..radiusZ) {
                val bl2 = j == -radiusZ || j == radiusZ
                val bl4 = bl && bl2
                val bl5 = (bl || bl2) && !bl4
                if (bl4 || bl5 && (config.extraEdgeColumnChance == 0.0f || random.nextFloat() > config.extraEdgeColumnChance)) continue
                mutable[pos, i, 0] = j
                var k = 0
                while (world.testBlockState(mutable) { it.isIn(MOSS_GROWS_UNDER) } && k < config.verticalRange) {
                    mutable.move(direction)
                    ++k
                }
                k = 0
                while (world.testBlockState(mutable) { !it.isIn(MOSS_GROWS_UNDER) } && k < config.verticalRange) {
                    mutable.move(direction2)
                    ++k
                }
                mutable2[mutable] = config.surface.direction
                val blockState = world.getBlockState(mutable2)
                if (!world.getBlockState(mutable).isIn(MOSS_GROWS_UNDER) ||
                    !blockState.isSideSolidFullSquare(world, mutable2, config.surface.direction.opposite)
                ) continue
                val l =
                    config.depth[random] + if (config.extraBottomBlockChance > 0.0f && random.nextFloat() < config.extraBottomBlockChance) 1 else 0
                val blockPos = mutable2.toImmutable()
                if (!placeGround(world, config, replaceable, random, mutable2, l)) continue
                set.add(blockPos.offset(config.surface.direction.opposite))
            }
        }
        return set.toMutableSet()
    }

    override fun generateVegetationFeature(
        world: StructureWorldAccess, config: VegetationPatchFeatureConfig,
        generator: ChunkGenerator, random: RandomGenerator, pos: BlockPos,
    ): Boolean =
        world.getBlockState(pos).isAir && config.vegetationFeature.value().place(world, generator, random, pos)
}
