package org.teamvoided.nullium.module

import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributes
import org.teamvoided.nullium.config.NulConfigManager
import org.teamvoided.nullium.util.attributeAsSet

object MobScale {
    fun init(entity: Entity) {
        val cfg = NulConfigManager.bigSalmon.data().scales
        if (cfg.isNotEmpty()) {
            if (entity !is LivingEntity) return
            val properties = cfg.firstOrNull { it.entity == entity.type } ?: return
            val scale = entity.attributeAsSet().firstOrNull { it.attribute == EntityAttributes.GENERIC_SCALE }
            if (scale == null) {
                entity.getAttributeInstance(EntityAttributes.GENERIC_SCALE)!!.baseValue = properties.getScale()
            }
        }
    }
}
