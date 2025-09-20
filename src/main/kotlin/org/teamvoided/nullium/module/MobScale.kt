package org.teamvoided.nullium.module

import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation.ADD_VALUE
import net.minecraft.entity.attribute.EntityAttributes.GENERIC_SCALE
import org.teamvoided.nullium.Nullium.CONFIG
import org.teamvoided.nullium.Nullium.id
import org.teamvoided.nullium.data.nullium.data.MobScaler.Companion.getScaler

object MobScale {
    val ID = id("mob_scaler")

    fun init(entity: Entity) {
        if (entity !is LivingEntity) return
        val scaleAtt = entity.getAttributeInstance(GENERIC_SCALE)!!
        if (!CONFIG.entities.customizableMobScale) {
            scaleAtt.removeModifier(ID)
            return
        }

        val scaler = entity.getScaler() ?: return
        val modifier = scaleAtt.getModifier(ID)

        if (modifier == null || scaler.provider.min > modifier.amount() || modifier.amount() > scaler.provider.max) {
            scaleAtt.removeModifier(ID)
            scaleAtt.addPersistentModifier(
                EntityAttributeModifier(ID, scaler[entity.world.random].toDouble(), ADD_VALUE)
            )
        }
    }
}
