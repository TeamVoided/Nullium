package org.teamvoided.nullium.data.gen.tags

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags
import net.minecraft.block.Blocks
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.tag.BlockTags
import org.teamvoided.nullium.data.tags.NulliumBlockTags
import java.util.concurrent.CompletableFuture

class BlockTagProvider(output: FabricDataOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>) :
    FabricTagProvider.BlockTagProvider(output, registriesFuture) {
    override fun configure(arg: HolderLookup.Provider) {
        //Nullium Tags
        getOrCreateTagBuilder(NulliumBlockTags.MOSS_GROWS_UNDER)
            .forceAddTag(BlockTags.REPLACEABLE)
            .forceAddTag(BlockTags.FLOWERS)
            .forceAddTag(BlockTags.LEAVES)
            .forceAddTag(BlockTags.SAPLINGS)
            .forceAddTag(BlockTags.BANNERS)
            .forceAddTag(BlockTags.SIGNS)
            .forceAddTag(BlockTags.CANDLES)
            .forceAddTag(BlockTags.BUTTONS)
            .forceAddTag(BlockTags.CORAL_PLANTS)
            .forceAddTag(BlockTags.CORALS)
            .forceAddTag(BlockTags.WALL_CORALS)
            .forceAddTag(ConventionalBlockTags.BUDS)
            .forceAddTag(ConventionalBlockTags.CLUSTERS)
            // Light
            .add(Blocks.TORCH)
            .add(Blocks.WALL_TORCH)
            .add(Blocks.REDSTONE_TORCH)
            .add(Blocks.REDSTONE_WALL_TORCH)
            .add(Blocks.SOUL_TORCH)
            .add(Blocks.SOUL_WALL_TORCH)
            .add(Blocks.LANTERN)
            .add(Blocks.SOUL_LANTERN)
            .add(Blocks.END_ROD)
            // Misc
            .add(Blocks.LEVER)
            .add(Blocks.CHAIN)
            .add(Blocks.TRIPWIRE)
            .add(Blocks.TRIPWIRE_HOOK)
            .add(Blocks.REDSTONE_WIRE)
            .add(Blocks.LIGHTNING_ROD)
            .add(Blocks.CONDUIT)
            .add(Blocks.COBWEB)
            // Plant
            .add(Blocks.MOSS_CARPET)
            .add(Blocks.RED_MUSHROOM)
            .add(Blocks.BROWN_MUSHROOM)
            .add(Blocks.WARPED_FUNGUS)
            .add(Blocks.CRIMSON_FUNGUS)
            .add(Blocks.SEA_PICKLE)
            .add(Blocks.BAMBOO)
            .add(Blocks.BAMBOO_SAPLING)
            .add(Blocks.SUGAR_CANE)
            .add(Blocks.SWEET_BERRY_BUSH)
            .add(Blocks.KELP)
            .add(Blocks.KELP_PLANT)
            .add(Blocks.BIG_DRIPLEAF_STEM)
            .add(Blocks.BIG_DRIPLEAF)
            .add(Blocks.SMALL_DRIPLEAF)
            .add(Blocks.COCOA)
            // Climbable Tag
            .add(
                Blocks.LADDER,
                Blocks.VINE,
                Blocks.SCAFFOLDING,
                Blocks.WEEPING_VINES,
                Blocks.WEEPING_VINES_PLANT,
                Blocks.TWISTING_VINES,
                Blocks.TWISTING_VINES_PLANT,
                Blocks.CAVE_VINES,
                Blocks.CAVE_VINES_PLANT
            )


        // Vanilla Tags
        getOrCreateTagBuilder(BlockTags.WALL_POST_OVERRIDE)
            .forceAddTag(BlockTags.BUTTONS)
            .add(Blocks.LEVER)

    }
}
