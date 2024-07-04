package org.teamvoided.nullium.init

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents
import net.fabricmc.fabric.api.loot.v2.LootTableEvents
import net.fabricmc.fabric.api.loot.v2.LootTableSource
import net.minecraft.block.Blocks
import net.minecraft.component.DataComponentTypes
import net.minecraft.entity.Entity
import net.minecraft.item.PotionItem
import net.minecraft.item.ThrowablePotionItem
import net.minecraft.loot.LootTable
import net.minecraft.loot.LootTables
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryKey
import net.minecraft.server.world.ServerWorld
import org.teamvoided.nullium.config.NulConfigManager
import org.teamvoided.nullium.data.loot.NulliumInjections
import org.teamvoided.nullium.module.Blacksmith
import org.teamvoided.nullium.module.MobScale
import org.teamvoided.nullium.util.lootPool

object NulFabricEvents {
    val cfg = NulConfigManager.main.data()

    fun init() {
        ServerEntityEvents.ENTITY_LOAD.register(::onEntityLoad)
        DefaultItemComponentEvents.MODIFY.register(::modifyDefaultItemComponent)
//        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(::onEndDataPackReload)
//        ServerWorldEvents.LOAD.register(::onWorldLoad)
        LootTableEvents.MODIFY.register(::modifyLootTable)
        if (cfg.stopping.enableBlacksmith) Blacksmith.repairOverrides()
    }

//    private fun onWorldLoad(server: MinecraftServer, world: ServerWorld) {
//    }
//
//    private fun onEndDataPackReload(server: MinecraftServer, ignored: AutoCloseableResourceManager, success: Boolean) {
//        if (!success) return
//    }

    private fun onEntityLoad(entity: Entity, ignored: ServerWorld) {
        if (cfg.stopping.enableMobScale) MobScale.init(entity)
    }

    private fun modifyLootTable(table: RegistryKey<LootTable>, builder: LootTable.Builder, ignored: LootTableSource) {
        if (cfg.reloadable.cakeDrops && table == Blocks.CAKE.lootTableId) {
            builder.pool(lootPool { lootTable(NulliumInjections.CAKE_DROPS) {} })
        }

        if (cfg.reloadable.barterUpgrades && table == LootTables.PIGLIN_BARTERING_GAMEPLAY) {
            builder.pool(lootPool { lootTable(NulliumInjections.BARTER_UPGRADES) {} })
        }
    }

    private fun modifyDefaultItemComponent(c: DefaultItemComponentEvents.ModifyContext) {
        if (cfg.stopping.enableStackablePotions) { // Myb move to custom file
            Registries.ITEM.filter { it is PotionItem && it !is ThrowablePotionItem }.forEach { item ->
                c.modify(item) { it.put(DataComponentTypes.MAX_STACK_SIZE, 16) }
            }
        }
    }
}