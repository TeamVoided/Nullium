package org.teamvoided.nullium.data.gen.tags

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.tag.FluidTags
import org.teamvoided.nullium.data.tags.NulliumFluidTags
import java.util.concurrent.CompletableFuture

class FluidTagProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) :
    FabricTagProvider.FluidTagProvider(o, r) {
    override fun configure(arg: HolderLookup.Provider) {

        getOrCreateTagBuilder(NulliumFluidTags.CANE_HYDRATION)
            .forceAddTag(FluidTags.WATER)
    }
}
