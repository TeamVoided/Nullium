package org.teamvoided.nullium.data.loot

import net.minecraft.registry.RegistryKeys
import org.teamvoided.nullium.Nullium.id
import org.teamvoided.nullium.util.key

object NulliumLootTables {
    val ENDERMAN_HOLDS = table("enderman/holds")
    val ENDERMAN_OVERWORLD_GENERIC = table("enderman/overworld_generic")
    val ENDERMAN_NETHER_GENERIC = table("enderman/nether_generic")
    val ENDERMAN_END_GENERIC = table("enderman/end_generic")
    val ENDERMAN_OVERWORLD_DESERT = table("enderman/overworld_desert")
    val ENDERMAN_OVERWORLD_BADLANDS = table("enderman/overworld_badlands")
    val ENDERMAN_OVERWORLD_ICE = table("enderman/overworld_ice")
    val ENDERMAN_OVERWORLD_FLOWER = table("enderman/overworld_flower")

    private fun table(id: String) = RegistryKeys.LOOT_TABLE.key(id(id))
}