package org.teamvoided.nullium.data.gen.tags

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.data.server.tag.StructureTags
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.RegistryKeys
import net.minecraft.world.gen.feature.StructureFeature
import net.minecraft.world.gen.structure.BuiltInStructures
import java.util.concurrent.CompletableFuture

class StructureTagProvider(output: FabricDataOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>) :
    FabricTagProvider<StructureFeature>(output, RegistryKeys.STRUCTURE_FEATURE, registriesFuture) {
    override fun configure(arg: HolderLookup.Provider) {
        //Cane Tags
        getOrCreateTagBuilder(StructureTags.EYE_OF_ENDER_LOCATED)
            .add(BuiltInStructures.END_CITY)

    }

}
