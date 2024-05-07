package org.teamvoided.nullium.module

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributes
import org.teamvoided.nullium.config.ConfigManager


@Suppress("UNCHECKED_CAST")
fun bigSalmon() {
    if (ConfigManager.bigSalmon.data().isNotEmpty()) {
        ServerEntityEvents.ENTITY_LOAD.register { entity, _ ->
            if (entity !is LivingEntity) return@register
            val properties =
                ConfigManager.bigSalmon.data()
                    .getOrElse(entity.type as EntityType<out LivingEntity>) { return@register }
            val scale = entity.attributes.tracked.firstOrNull { it.attribute == EntityAttributes.GENERIC_SCALE }

            if (scale == null) {
                entity.getAttributeInstance(EntityAttributes.GENERIC_SCALE)!!.baseValue = properties.random()
            }
        }
    }
}
