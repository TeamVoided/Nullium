package org.teamvoided.nullium.data.gen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistrySetBuilder
import org.teamvoided.nullium.Nullium.log
import org.teamvoided.nullium.data.gen.dyn.MobScalerCreator
import org.teamvoided.nullium.data.gen.dyn.RepairDataCreator
import org.teamvoided.nullium.data.gen.dyn.VillagerFoodCreator
import org.teamvoided.nullium.data.gen.loot.EquipmentLootTablesProvider
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
        pack.addProvider(::EquipmentLootTablesProvider)
        InjectionLootTablesProvider.lootTables().forEach { pack.addProvider(it) }

        //special
        pack.addProvider(::DynFabricReg)
    }

    override fun buildRegistry(gen: RegistrySetBuilder) {
        gen.add(RegistryKeys.CONFIGURED_FEATURE, ConfiguredFeatureCreator::bootstrap)
        // Nullium
        gen.add(NulRegistryKeys.VILLAGER_FOOD, VillagerFoodCreator::bootstrap)
        gen.add(NulRegistryKeys.MOB_SCALER, MobScalerCreator::bootstrap)
        gen.add(NulRegistryKeys.REPAIR_DATA, RepairDataCreator::bootstrap)
    }

    class DynFabricReg(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) :
        FabricDynamicRegistryProvider(o, r) {
        override fun getName(): String = "Nullium/DynReg"
        override fun configure(reg: HolderLookup.Provider, entries: Entries) {
            entries.addAll(reg.getLookupOrThrow(RegistryKeys.CONFIGURED_FEATURE))
            // Nullium
            entries.addAll(reg.getLookupOrThrow(NulRegistryKeys.REPAIR_DATA))
            entries.addAll(reg.getLookupOrThrow(NulRegistryKeys.VILLAGER_FOOD))
            entries.addFull(reg.getLookupOrThrow(NulRegistryKeys.MOB_SCALER))
        }

        fun <T> Entries.addFull(lookup: HolderLookup.RegistryLookup<T>) {
            for (key in lookup.streamElementKeys()) {
                add(lookup, key)
            }
        }
    }
}
