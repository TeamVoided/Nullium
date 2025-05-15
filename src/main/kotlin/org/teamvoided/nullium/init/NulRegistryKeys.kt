package org.teamvoided.nullium.init

import net.fabricmc.fabric.api.event.registry.DynamicRegistries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.world.World
import org.teamvoided.nullium.Nullium.id
import org.teamvoided.nullium.data.custom.VillagerFood

object NulRegistryKeys {
    @JvmField
    val VILLAGER_FOOD: RegistryKey<Registry<VillagerFood>> = createRegistryKey("villager_food")

    fun init() {
        DynamicRegistries.registerSynced(VILLAGER_FOOD, VillagerFood.CODEC)
    }

    @Suppress("SameParameterValue")
    private fun <T> createRegistryKey(id: String): RegistryKey<Registry<T>> = RegistryKey.ofRegistry(id(id))


    fun World.getVillagerFood()= this.registryManager.get(VILLAGER_FOOD)
}
