package org.teamvoided.nullium.data.gen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistrySetBuilder
import org.teamvoided.nullium.Nullium.log
import org.teamvoided.nullium.data.gen.dyn.VillagerFoodCreator
import org.teamvoided.nullium.data.gen.loot.EntityLootTablesProvider
import org.teamvoided.nullium.data.gen.loot.InjectionLootTablesProvider
import org.teamvoided.nullium.data.gen.tags.*
import org.teamvoided.nullium.data.gen.world.gen.ConfiguredFeatureCreator
import org.teamvoided.nullium.init.NulRegistryKeys
import java.util.concurrent.CompletableFuture

@Suppress("unused")
class NulliumData : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
        log.info("Hello from DataGen")
        val pack = gen.createPack()

        //tags
        pack.addProvider(::ItemTagProvider)
        pack.addProvider(::BlockTagProvider)
        pack.addProvider(::FluidTagProvider)
        pack.addProvider(::StructureTagProvider)
        pack.addProvider(::ProfessionTagProvider)
        //loot tables
        pack.addProvider(::EntityLootTablesProvider)
        InjectionLootTablesProvider.lootTables().forEach { pack.addProvider(it) }

        //special
        pack.addProvider(::DynFabricReg)
    }

    override fun buildRegistry(gen: RegistrySetBuilder) {
        gen.add(RegistryKeys.CONFIGURED_FEATURE, ConfiguredFeatureCreator::bootstrap)
        // Custom
        gen.add(NulRegistryKeys.VILLAGER_FOOD, VillagerFoodCreator::bootstrap)
    }

    class DynFabricReg(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) :
        FabricDynamicRegistryProvider(o, r) {
        override fun getName(): String = "Nullium/DynReg"
        override fun configure(reg: HolderLookup.Provider, entries: Entries) {
            entries.addAll(reg.getLookupOrThrow(RegistryKeys.CONFIGURED_FEATURE))
            // Custom
            entries.addAll(reg.getLookupOrThrow(NulRegistryKeys.VILLAGER_FOOD))
        }
    }
}
