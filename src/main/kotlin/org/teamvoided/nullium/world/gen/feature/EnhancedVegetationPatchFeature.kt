package org.teamvoided.nullium.world.gen.feature

import com.mojang.serialization.Codec
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.util.random.RandomGenerator
import net.minecraft.world.StructureWorldAccess
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.util.FeatureContext
import java.util.function.Predicate
import org.teamvoided.nullium.world.gen.feature.cfg.EnhancedVegetationPatchFeatureConfig as EVPFConfig

open class EnhancedVegetationPatchFeature(codec: Codec<EVPFConfig>) : Feature<EVPFConfig>(codec) {
    override fun place(context: FeatureContext<EVPFConfig>): Boolean {
        val world = context.world
        val config: EVPFConfig = context.getConfig()
        val random = context.random
        val originPos = context.origin
        val canReplace = Predicate { state: BlockState -> state.isIn(config.replaceable) }
        val canGrowUnder = Predicate { state: BlockState -> state.isIn(config.canGrowUnder) }
        val xRadius = config.horizontalRadius.get(random) + 1
        val yRadius = config.horizontalRadius.get(random) + 1
        val vegetationSpaces: MutableSet<BlockPos> = placeGroundAndGetPositions(
            world, config, random, originPos, canReplace, canGrowUnder, xRadius, yRadius
        )
        generateVegetation(context, world, config, random, vegetationSpaces)
        return !vegetationSpaces.isEmpty()
    }


    protected fun placeGroundAndGetPositions(
        world: StructureWorldAccess,
        config: EVPFConfig,
        random: RandomGenerator,
        originPos: BlockPos,
        replaceable: Predicate<BlockState>,
        canGrowUnder: Predicate<BlockState>,
        radiusX: Int,
        radiusZ: Int,
    ): MutableSet<BlockPos> {
        val pos = originPos.mutableCopy()
        val posCopy = pos.mutableCopy()
        val surfaceDir = config.surface.direction
        val oppositeDir = surfaceDir.opposite
        val vegetationSpaces = hashSetOf<BlockPos>()


        for (i in -radiusX..radiusX) {
            val xEdge = i == -radiusX || i == radiusX
            for (j in -radiusZ..radiusZ) {
                val zEdge = j == -radiusZ || j == radiusZ
                val edge = xEdge || zEdge
                val corner = xEdge && zEdge
                val notCorner = edge && !corner
                if (corner || notCorner && (config.extraEdgeColumnChance == 0.0f || random.nextFloat() > config.extraEdgeColumnChance)) continue
                pos.set(originPos, i, 0, j)

                for (ignored in 0 until config.verticalRange) {
                    if (!world.testBlockState(pos, canGrowUnder)) break
                    pos.move(surfaceDir)
                }

                for (ignored in 0 until config.verticalRange) {
                    if (!world.testBlockState(pos, canGrowUnder.negate())) break
                    pos.move(oppositeDir)
                }

                posCopy.set(pos, surfaceDir)

                if (!world.testBlockState(pos, canGrowUnder)) continue
                if (!world.getBlockState(posCopy).isSideSolidFullSquare(world, posCopy, oppositeDir)) continue
                val extraBottomBlocks =
                    if (config.extraBottomBlockChance > 0.0f && random.nextFloat() < config.extraBottomBlockChance) 1 else 0
                val depth = config.depth.get(random) + extraBottomBlocks

                val blockPos = posCopy.toImmutable()
                if (this.placeGround(world, config, replaceable, random, posCopy, depth)) {
                    vegetationSpaces.add(blockPos)
                }
            }
        }
        return vegetationSpaces
    }


    open fun generateVegetation(
        context: FeatureContext<EVPFConfig>, world: StructureWorldAccess,
        config: EVPFConfig, random: RandomGenerator, positions: MutableSet<BlockPos>,
    ) {
        if (config.vegetationChance <= 0.0f) return
        for (blockPos in positions) {
            if (random.nextFloat() < config.vegetationChance) {
                generateVegetationFeature(world, config, context.generator, random, blockPos)
            }
        }
    }

    open fun generateVegetationFeature(
        world: StructureWorldAccess, config: EVPFConfig, generator: ChunkGenerator,
        random: RandomGenerator, inPos: BlockPos,
    ): Boolean {
        val pos = inPos.offset(config.surface.direction.opposite)
        return world.getBlockState(pos).isAir && config.vegetationFeature.value().place(world, generator, random, pos)
    }

    open fun placeGround(
        world: StructureWorldAccess, config: EVPFConfig, replaceable: Predicate<BlockState>,
        random: RandomGenerator, pos: BlockPos.Mutable, depth: Int,
    ): Boolean {
        for (i in 0..<depth) {
            val state = config.groundState.getBlockState(random, pos)
            val existingState = world.getBlockState(pos)
            if (state.isOf(existingState.block)) continue
            if (!replaceable.test(existingState)) {
                return i != 0
            }
            world.setBlockState(pos, state, Block.NOTIFY_LISTENERS)
            pos.move(config.surface.direction)
        }
        return true
    }
}