package org.teamvoided.nullium.data.gen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.minecraft.registry.RegistrySetBuilder
import org.teamvoided.nullium.Nullium.log
import org.teamvoided.nullium.data.gen.tags.BlockTagProvider
import org.teamvoided.nullium.data.gen.tags.FluidTagProvider
import org.teamvoided.nullium.data.gen.tags.StructureTagProvider

class NulliumData : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
        log.info("Hello from DataGen")
        val pack = gen.createPack()

        pack.addProvider(::BlockTagProvider)
        pack.addProvider(::FluidTagProvider)
        pack.addProvider(::StructureTagProvider)
    }

    override fun buildRegistry(gen: RegistrySetBuilder) {
//        gen.add(RegistryKeys.BIOME, TemplateBiomes::boostrap)
    }
}
