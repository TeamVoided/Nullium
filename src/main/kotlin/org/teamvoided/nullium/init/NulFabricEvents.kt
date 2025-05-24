package org.teamvoided.nullium.init

import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents
import net.fabricmc.fabric.api.loot.v3.LootTableEvents
import net.fabricmc.fabric.api.loot.v3.LootTableSource
import net.minecraft.block.Blocks
import net.minecraft.component.DataComponentTypes
import net.minecraft.entity.Entity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.FoodComponent
import net.minecraft.item.Items
import net.minecraft.item.PotionItem
import net.minecraft.item.ThrowablePotionItem
import net.minecraft.loot.LootTable
import net.minecraft.loot.LootTables
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryKey
import net.minecraft.server.world.ServerWorld
import org.teamvoided.nullium.cfg.NulConfig
import org.teamvoided.nullium.config.NulConfigManager
import org.teamvoided.nullium.data.custom.VillagerFood
import org.teamvoided.nullium.data.loot.NulliumInjections
import org.teamvoided.nullium.module.Blacksmith
import org.teamvoided.nullium.module.MobScale
import org.teamvoided.nullium.util.lootPool

@Suppress("UNUSED_PARAMETER")
object NulFabricEvents {
    val cfg = NulConfigManager.main.data()
    @JvmField
    var config = ConfigApi.registerAndLoadConfig(::NulConfig)
    fun init() {
        ServerEntityEvents.ENTITY_LOAD.register(::onEntityLoad)
        DefaultItemComponentEvents.MODIFY.register(::modifyDefaultItemComponent)
        LootTableEvents.MODIFY.register(::modifyLootTable)
        if (cfg.enableBlacksmith()) Blacksmith.repairOverrides()

        ServerLifecycleEvents.START_DATA_PACK_RELOAD.register { server, serverResourceManager ->
            VillagerFood.visitedFoods.clear()
        }
    }


    private fun onEntityLoad(entity: Entity, ignored: ServerWorld) {
        if (cfg.enableMobScale()) MobScale.init(entity)
    }

    private fun modifyLootTable(
        table: RegistryKey<LootTable>, builder: LootTable.Builder,
        ignored: LootTableSource, provider: HolderLookup.Provider,
    ) {
        if (config.cakeDrops && table == Blocks.CAKE.lootTableId) {
            builder.pool(lootPool { lootTable(NulliumInjections.CAKE_DROPS) {} })
        }

        if (config.barterUpgrades && table == LootTables.PIGLIN_BARTERING_GAMEPLAY) {
            builder.pool(lootPool { lootTable(NulliumInjections.BARTER_UPGRADES) {} })
        }
    }

    private fun modifyDefaultItemComponent(c: DefaultItemComponentEvents.ModifyContext) {
        if (cfg.enableStackablePotions()) {
            Registries.ITEM.filter { it is PotionItem && it !is ThrowablePotionItem }.forEach { item ->
                c.modify(item) { it.put(DataComponentTypes.MAX_STACK_SIZE, 16) }
            }
        }

        if (cfg.enableStackableSaddles()) c.modify(Items.SADDLE) { it.put(DataComponentTypes.MAX_STACK_SIZE, 16) }

        if (cfg.enableStackableHorseArmor()) {
            listOf(
                Items.LEATHER_HORSE_ARMOR, Items.IRON_HORSE_ARMOR, Items.GOLDEN_HORSE_ARMOR, Items.DIAMOND_HORSE_ARMOR
            ).forEach { item ->
                c.modify(item) { it.put(DataComponentTypes.MAX_STACK_SIZE, 16) }
            }
        }

        if (cfg.enableStackableMusicDiscs()) {
            Registries.ITEM.filter { it.components.get(DataComponentTypes.JUKEBOX_PLAYABLE) != null }.forEach { item ->
                c.modify(item) { it.put(DataComponentTypes.MAX_STACK_SIZE, 16) }
            }
        }

        if (cfg.enableStackableMinecarts()) c.modify(Items.MINECART) { it.put(DataComponentTypes.MAX_STACK_SIZE, 4) }

        if (cfg.enableGlowBerriesGlow()) c.modify(Items.GLOW_BERRIES) {
            it.put(
                DataComponentTypes.FOOD, FoodComponent.Builder()
                    .hunger(2)
                    .saturation(0.1f)
                    .statusEffect(StatusEffectInstance(StatusEffects.GLOWING, 600, 0), 1.0f)
                    .build()
            )
        }
    }
}