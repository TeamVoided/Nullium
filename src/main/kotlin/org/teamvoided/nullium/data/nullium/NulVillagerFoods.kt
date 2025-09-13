package org.teamvoided.nullium.data.nullium

import net.minecraft.registry.RegistryKey
import org.teamvoided.nullium.Nullium.id
import org.teamvoided.nullium.data.nullium.data.VillagerFood
import org.teamvoided.nullium.init.NulRegistryKeys

object NulVillagerFoods {
    // Vanilla
    val BREAD = create("bread")
    val POTATO = create("potato")
    val CARROT = create("carrot")
    val BEETROOT = create("beetroot")

    // Nullium
    val PIES = create("pies")

    fun create(path: String): RegistryKey<VillagerFood> = RegistryKey.of(NulRegistryKeys.VILLAGER_FOOD, id(path))
}