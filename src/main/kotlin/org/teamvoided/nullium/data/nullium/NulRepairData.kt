package org.teamvoided.nullium.data.nullium

import net.minecraft.registry.RegistryKey
import org.teamvoided.nullium.Nullium.id
import org.teamvoided.nullium.data.nullium.data.MobScaler
import org.teamvoided.nullium.data.nullium.data.RepairData
import org.teamvoided.nullium.init.NulRegistryKeys

object NulRepairData {
    // Materials
    val WOOD = material("wood")
    val STONE = material("stone")
    val GOLD = material("gold")
    val IRON = material("iron")
    val DIAMOND = material("diamond")
    val NETHERITE = material("netherite")
    val LEATHER = material("leather")
    val CHAIN = material("chain")

    // Items
    val MACE = create("mace")
    val TRIDENT = create("trident")
    val BOW = create("bow")
    val CROSSBOW = create("crossbow")
    val SHIELD = create("shield")
    val ELYTRA = create("elytra")
    val SHEARS = create("shears")
    val FISHING_ROD = create("fishing_rod")

    fun material(id: String): RegistryKey<RepairData> = RegistryKey.of(NulRegistryKeys.REPAIR_DATA, id("material/$id"))
    fun create(id: String): RegistryKey<RepairData> = RegistryKey.of(NulRegistryKeys.REPAIR_DATA, id(id))
}