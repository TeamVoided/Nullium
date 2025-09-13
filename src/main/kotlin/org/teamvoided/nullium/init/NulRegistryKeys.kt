package org.teamvoided.nullium.init

import net.fabricmc.fabric.api.event.registry.DynamicRegistries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import org.teamvoided.nullium.Nullium.id
import org.teamvoided.nullium.data.nullium.data.MobScaler
import org.teamvoided.nullium.data.nullium.data.RepairData
import org.teamvoided.nullium.data.nullium.data.VillagerFood

object NulRegistryKeys {
    @JvmField
    val VILLAGER_FOOD: RegistryKey<Registry<VillagerFood>> = createRegistryKey("villager_food")

    @JvmField
    val MOB_SCALER: RegistryKey<Registry<MobScaler>> = createRegistryKey("mob_scaler")

    @JvmField
    val REPAIR_DATA: RegistryKey<Registry<RepairData>> = createRegistryKey("repair_data")

    fun init() {
        DynamicRegistries.registerSynced(VILLAGER_FOOD, VillagerFood.CODEC)
        DynamicRegistries.registerSynced(MOB_SCALER, MobScaler.CODEC)
        DynamicRegistries.registerSynced(REPAIR_DATA, RepairData.CODEC)
    }

    @Suppress("SameParameterValue")
    private fun <T> createRegistryKey(id: String): RegistryKey<Registry<T>> = RegistryKey.ofRegistry(id(id))
}
