package org.teamvoided.nullium.data.custom

import net.minecraft.entity.EntityType
import net.minecraft.registry.RegistryKey
import net.minecraft.util.Identifier
import org.teamvoided.nullium.init.NulRegistryKeys

object NulMobScalers {
    // Nullium
    val SALMON = create(EntityType.SALMON)
    val COD = create(EntityType.COD)
    val TROPICAL_FISH = create(EntityType.TROPICAL_FISH)
    val SQUID = create(EntityType.SQUID)
    val GLOW_SQUID = create(EntityType.GLOW_SQUID)

    fun create(mob: EntityType<*>) = create(EntityType.getId(mob))
    fun create(id: Identifier): RegistryKey<MobScaler> = RegistryKey.of(NulRegistryKeys.MOB_SCALER, id)
}