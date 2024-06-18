package org.teamvoided.nullium.init

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents
import net.minecraft.component.DataComponentTypes
import net.minecraft.entity.Entity
import net.minecraft.item.PotionItem
import net.minecraft.item.ThrowablePotionItem
import net.minecraft.registry.Registries
import net.minecraft.server.world.ServerWorld
import org.teamvoided.nullium.config.NulConfigManager
import org.teamvoided.nullium.module.BigSalmon

object NulFabricEvents {

    fun init() {
        ServerEntityEvents.ENTITY_LOAD.register(::onEntityLoad)
        DefaultItemComponentEvents.MODIFY.register(::modifyDefaultItemComponent)
    }

    private fun modifyDefaultItemComponent(c: DefaultItemComponentEvents.ModifyContext) {
        val cfg = NulConfigManager.switchboard.data()
        if (cfg.enableStackablePotions) { // Myb move to custom file
            Registries.ITEM.filter { it is PotionItem && it !is ThrowablePotionItem }.forEach { item ->
                c.modify(item) { it.put(DataComponentTypes.MAX_STACK_SIZE, 16) }
            }
        }
    }


    private fun onEntityLoad(entity: Entity, ignored: ServerWorld) {
        val cfg = NulConfigManager.switchboard.data()
        if (cfg.enableBigSalmon) BigSalmon.init(entity)
    }
}