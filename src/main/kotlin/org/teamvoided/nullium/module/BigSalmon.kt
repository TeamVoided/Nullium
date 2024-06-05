package org.teamvoided.nullium.module

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributes
import org.teamvoided.nullium.config.NulConfigManager

@Suppress("UNCHECKED_CAST")
object BigSalmon {
    fun init() {
        if (NulConfigManager.bigSalmon.data().isNotEmpty()) {
            ServerEntityEvents.ENTITY_LOAD.register { entity, _ ->
                if (entity !is LivingEntity) return@register
                val properties =
                    NulConfigManager.bigSalmon.data()
                        .getOrElse(entity.type as EntityType<out LivingEntity>) { return@register }
                val scale = entity.attributes.tracked.firstOrNull { it.attribute == EntityAttributes.GENERIC_SCALE }

                if (scale == null) {
                    entity.getAttributeInstance(EntityAttributes.GENERIC_SCALE)!!.baseValue = properties.random()
                }
            }
        }
    }
}
