package org.teamvoided.nullium.data.gen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.RegistrySetBuilder
import org.teamvoided.nullium.Nullium.log
import org.teamvoided.nullium.data.gen.dyn.VillagerFoodCreator
import org.teamvoided.nullium.data.gen.loot.EntityLootTablesProvider
import org.teamvoided.nullium.data.gen.loot.InjectionLootTablesProvider
import org.teamvoided.nullium.data.gen.tags.BlockTagProvider
import org.teamvoided.nullium.data.gen.tags.FluidTagProvider
import org.teamvoided.nullium.data.gen.tags.ItemTagProvider
import org.teamvoided.nullium.data.gen.tags.StructureTagProvider
import org.teamvoided.nullium.init.NulRegistryKeys
import java.util.concurrent.CompletableFuture

class NulliumData : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
        log.info("Hello from DataGen")
        val pack = gen.createPack()

        //tags
        pack.addProvider(::ItemTagProvider)
        pack.addProvider(::BlockTagProvider)
        pack.addProvider(::FluidTagProvider)
        pack.addProvider(::StructureTagProvider)
        //loot tables
        pack.addProvider(::EntityLootTablesProvider)
        InjectionLootTablesProvider.lootTables().forEach { pack.addProvider(it) }

        //special
        pack.addProvider(::DynFabricReg)
    }

    override fun buildRegistry(gen: RegistrySetBuilder) {
        gen.add(NulRegistryKeys.VILLAGER_FOOD, VillagerFoodCreator::bootstrap)
    }

    class DynFabricReg(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) :
        FabricDynamicRegistryProvider(o, r) {
        override fun getName(): String = "Nullium/DynReg"
        override fun configure(reg: HolderLookup.Provider, entries: Entries) {
            entries.addAll(reg.getLookupOrThrow(NulRegistryKeys.VILLAGER_FOOD))
        }
    }
}
