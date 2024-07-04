package org.teamvoided.nullium.data.gen.loot

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider
import net.minecraft.block.Blocks
import net.minecraft.block.CakeBlock
import net.minecraft.item.Items
import net.minecraft.loot.LootTable
import net.minecraft.loot.context.LootContextType
import net.minecraft.loot.context.LootContextTypes
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.RegistryKey
import org.teamvoided.nullium.data.loot.NulliumInjections
import org.teamvoided.nullium.util.blockStateProperty
import org.teamvoided.nullium.util.lootTable
import org.teamvoided.nullium.util.randomChance
import org.teamvoided.nullium.util.survivesExplosion
import java.util.concurrent.CompletableFuture
import java.util.function.BiConsumer

typealias SimpleLootProviderFactory = (o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) -> SimpleFabricLootTableProvider

object InjectionLootTablesProvider {
    fun lootTables(): List<SimpleLootProviderFactory> {
        return listOf(
            simpleLootTableProvider(LootContextTypes.BLOCK) { gen ->
                gen.accept(
                    NulliumInjections.CAKE_DROPS,
                    lootTable {
                        pool {
                            item(Items.CAKE) {
                                conditionally(
                                    blockStateProperty(Blocks.CAKE) {
                                        exactMatch(CakeBlock.BITES, 0)
                                    }
                                )
                            }
                            conditionally(survivesExplosion())
                        }
                    }
                )
            },
            simpleLootTableProvider(LootContextTypes.BARTER) { gen ->
                gen.accept(
                    NulliumInjections.BARTER_UPGRADES,
                    lootTable {
                        pool {
                            item(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE) {
                                conditionally(randomChance(0.5f))
                            }
                        }
                    }
                )
            }
        )
    }

    fun simpleLootTableProvider(
        c: LootContextType, fn: (gen: BiConsumer<RegistryKey<LootTable>, LootTable.Builder>) -> Unit
    ): SimpleLootProviderFactory {
        return { o, r ->
            object : SimpleFabricLootTableProvider(o, r, c) {
                override fun generate(gen: BiConsumer<RegistryKey<LootTable>, LootTable.Builder>) {
                    fn(gen)
                }
                override fun getName(): String = "Injection|${super.getName()}"
            }
        }
    }
}


