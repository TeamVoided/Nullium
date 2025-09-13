package org.teamvoided.nullium.module

import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributes.GENERIC_SCALE
import org.teamvoided.nullium.Nullium.CONFIG
import org.teamvoided.nullium.data.custom.MobScaler.Companion.getScaler
import org.teamvoided.nullium.util.attributeAsSet

object MobScale {
    fun init(entity: Entity) {
        if (!CONFIG.customizableMobScale) return
        if (entity !is LivingEntity) return

        val scaler = entity.getScaler() ?: return
        val scale = entity.attributeAsSet().firstOrNull { it.attribute == GENERIC_SCALE }?.baseValue

        if (scale == null || scaler.provider.min > scale || scale > scaler.provider.max) {
            entity.getAttributeInstance(GENERIC_SCALE)!!.baseValue = scaler[entity.world.random].toDouble()
        }
    }
}
