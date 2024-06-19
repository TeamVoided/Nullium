package org.teamvoided.nullium.data.loot

import net.minecraft.registry.RegistryKeys
import org.teamvoided.nullium.Nullium.id
import org.teamvoided.nullium.util.key

object NulliumInjections {
    val CAKE_DROPS = inject("cake_drops")
    val BARTER_UPGRADES = inject("barter_upgrades")


    private fun inject(id: String) = RegistryKeys.LOOT_TABLE.key(id("injections/$id"))
}