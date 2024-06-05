package org.teamvoided.nullium.data.gen.tags

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.block.Blocks
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.tag.BlockTags
import org.teamvoided.nullium.data.NulliumBlockTags
import java.util.concurrent.CompletableFuture

class BlockTagProvider(output: FabricDataOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>) :
    FabricTagProvider.BlockTagProvider(output, registriesFuture) {
    override fun configure(arg: HolderLookup.Provider) {
        //Cane Tags
        getOrCreateTagBuilder(NulliumBlockTags.CANE_HYDRATION)
            .add(Blocks.ICE)
            .add(Blocks.FROSTED_ICE)
        getOrCreateTagBuilder(NulliumBlockTags.CANE_SUPPORT)
            .forceAddTag(BlockTags.DIRT)
            .forceAddTag(BlockTags.SAND)

        getOrCreateTagBuilder(NulliumBlockTags.WITHER_ROSE_PLACEABLE)
            .forceAddTag(BlockTags.DIRT)
            .add(Blocks.FARMLAND)
            .add(Blocks.NETHERRACK)
            .add(Blocks.SOUL_SOIL)
            .add(Blocks.SOUL_SAND)

        getOrCreateTagBuilder(NulliumBlockTags.SUPPORT_SMALL_TOP)
            .forceAddTag(BlockTags.FENCES)
            .forceAddTag(BlockTags.WALLS)

        getOrCreateTagBuilder(NulliumBlockTags.SUPPORT_SMALL_BOTTOM)
            .forceAddTag(BlockTags.FENCES)
            .forceAddTag(BlockTags.WALLS)

    }
}