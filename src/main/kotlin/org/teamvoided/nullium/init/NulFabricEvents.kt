package org.teamvoided.nullium.init

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents
import net.fabricmc.fabric.api.loot.v2.LootTableEvents
import net.fabricmc.fabric.api.loot.v2.LootTableSource
import net.minecraft.block.Blocks
import net.minecraft.component.DataComponentTypes
import net.minecraft.entity.Entity
import net.minecraft.item.Items
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

@Suppress("UNUSED_PARAMETER")
object NulFabricEvents {
    val cfg = NulConfigManager.main.data()

    fun init() {
        ServerEntityEvents.ENTITY_LOAD.register(::onEntityLoad)
        DefaultItemComponentEvents.MODIFY.register(::modifyDefaultItemComponent)
        LootTableEvents.MODIFY.register(::modifyLootTable)
        if (cfg.stopping.enableBlacksmith) Blacksmith.repairOverrides()
    }


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
        if (cfg.stopping.enableStackablePotions) {
            Registries.ITEM.filter { it is PotionItem && it !is ThrowablePotionItem }.forEach { item ->
                c.modify(item) { it.put(DataComponentTypes.MAX_STACK_SIZE, 16) }
            }
        }

//        cfg.stopping.enableStackableSaddles
        if (true) c.modify(Items.SADDLE) { it.put(DataComponentTypes.MAX_STACK_SIZE, 16) }

//        cfg.stopping.enableStackableHorseArmor
        if (true) {
            listOf(
                Items.LEATHER_HORSE_ARMOR, Items.IRON_HORSE_ARMOR, Items.GOLDEN_HORSE_ARMOR, Items.DIAMOND_HORSE_ARMOR
            ).forEach { item ->
                c.modify(item) { it.put(DataComponentTypes.MAX_STACK_SIZE, 16) }
            }
        }

//        cfg.stopping.enableStackableMusicDiscs
        if (true) {
            Registries.ITEM.filter { it.components.get(DataComponentTypes.JUKEBOX_PLAYABLE) != null }.forEach { item ->
                c.modify(item) { it.put(DataComponentTypes.MAX_STACK_SIZE, 16) }
            }
        }

//        cfg.stopping.enableStackableSaddles
        if (true) c.modify(Items.MINECART) { it.put(DataComponentTypes.MAX_STACK_SIZE, 4) }

    }
}