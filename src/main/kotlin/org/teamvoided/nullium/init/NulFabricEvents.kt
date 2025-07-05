package org.teamvoided.nullium.init

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
import net.minecraft.loot.LootTable
import net.minecraft.loot.LootTables
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.RegistryKey
import net.minecraft.resource.AutoCloseableResourceManager
import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import org.teamvoided.nullium.Nullium.CONFIG
import org.teamvoided.nullium.config.NulConfigManager
import org.teamvoided.nullium.data.custom.VillagerFood
import org.teamvoided.nullium.data.loot.NulliumInjections
import org.teamvoided.nullium.module.Compostable
import org.teamvoided.nullium.module.MobScale
import org.teamvoided.nullium.util.lootPool

@Suppress("UNUSED_PARAMETER")
object NulFabricEvents {
    val cfg = NulConfigManager.main.data()

    fun init() {
        ServerLifecycleEvents.START_DATA_PACK_RELOAD.register(::onStartDataPackReload)
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(::onEndDataPackReload)

        ServerEntityEvents.ENTITY_LOAD.register(::onEntityLoad)
        DefaultItemComponentEvents.MODIFY.register(::modifyDefaultItemComponent)
        LootTableEvents.MODIFY.register(::modifyLootTable)

    }

    private fun onStartDataPackReload(server: MinecraftServer, manager: AutoCloseableResourceManager) {
        VillagerFood.visitedFoods.clear()
    }

    private fun onEndDataPackReload(server: MinecraftServer, manager: AutoCloseableResourceManager, success: Boolean) {
        Compostable.init()
    }


    private fun onEntityLoad(entity: Entity, ignored: ServerWorld) {
        if (cfg.enableMobScale()) MobScale.init(entity)
    }

    private fun modifyLootTable(
        table: RegistryKey<LootTable>, builder: LootTable.Builder,
        ignored: LootTableSource, provider: HolderLookup.Provider,
    ) {
        if (CONFIG.cakeDrops && table == Blocks.CAKE.lootTableId) {
            builder.pool(lootPool { lootTable(NulliumInjections.CAKE_DROPS) {} })
        }

        if (CONFIG.barterUpgrades && table == LootTables.PIGLIN_BARTERING_GAMEPLAY) {
            builder.pool(lootPool { lootTable(NulliumInjections.BARTER_UPGRADES) {} })
        }
    }

    private fun modifyDefaultItemComponent(ctx: DefaultItemComponentEvents.ModifyContext) {
        if (CONFIG.glowBerriesGlowing) ctx.modify(Items.GLOW_BERRIES) {
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