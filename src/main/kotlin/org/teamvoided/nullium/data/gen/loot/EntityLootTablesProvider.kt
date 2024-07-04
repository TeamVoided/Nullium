package org.teamvoided.nullium.data.gen.loot

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags
import net.minecraft.item.Items
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.LocationCheckLootCondition
import net.minecraft.loot.context.LootContextTypes
import net.minecraft.loot.entry.EmptyEntry
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.entry.LootTableEntry
import net.minecraft.predicate.entity.LocationPredicate
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import org.teamvoided.nullium.data.loot.NulliumInjections
import org.teamvoided.nullium.data.loot.NulliumLootTables
import org.teamvoided.nullium.util.lootTable
import org.teamvoided.nullium.util.randomChance
import java.util.concurrent.CompletableFuture
import java.util.function.BiConsumer

class EntityLootTablesProvider(o: FabricDataOutput, val r: CompletableFuture<HolderLookup.Provider>) :
    SimpleFabricLootTableProvider(o, r, LootContextTypes.ENTITY) {
    override fun generate(gen: BiConsumer<RegistryKey<LootTable>, LootTable.Builder>) {
        val biomes = r.get().getLookupOrThrow(RegistryKeys.BIOME)
        gen.accept(
            NulliumInjections.BARTER_UPGRADES,
            lootTable {
                pool {
                    item(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE) {
                        conditionally(randomChance(0.04f))
                    }
                }
            }
        )
        gen.accept(
            NulliumLootTables.ENDERMAN_HOLDS,
            LootTable.builder().pool(
                LootPool.builder().rolls(Utils.constantNum(1))
                    .with(EmptyEntry.builder().weight(1000))
                    .with(LootTableEntry.method_428(NulliumLootTables.ENDERMAN_OVERWORLD_GENERIC).weight(25))
                    .with(
                        LootTableEntry.method_428(NulliumLootTables.ENDERMAN_NETHER_GENERIC)
                            .conditionally(
                                LocationCheckLootCondition.builder(
                                    LocationPredicate.Builder.create().method_9024(
                                        biomes.getTagOrThrow(ConventionalBiomeTags.IS_NETHER)
                                    )
                                )
                            )
                    )
                    .with(
                        LootTableEntry.method_428(NulliumLootTables.ENDERMAN_END_GENERIC)
                            .conditionally(
                                LocationCheckLootCondition.builder(
                                    LocationPredicate.Builder.create().method_9024(
                                        biomes.getTagOrThrow(ConventionalBiomeTags.IS_END)
                                    )
                                )
                            )
                    )
                    .with(
                        LootTableEntry.method_428(NulliumLootTables.ENDERMAN_OVERWORLD_FLOWER).conditionally(
                            LocationCheckLootCondition.builder(
                                LocationPredicate.Builder.create().method_9024(
                                    biomes.getTagOrThrow(ConventionalBiomeTags.IS_FLORAL)
                                )
                            )
                        ).weight(50)
                    )
                    .with(
                        LootTableEntry.method_428(NulliumLootTables.ENDERMAN_OVERWORLD_ICE).conditionally(
                            LocationCheckLootCondition.builder(
                                LocationPredicate.Builder.create().method_9024(
                                    biomes.getTagOrThrow(ConventionalBiomeTags.IS_COLD_OVERWORLD)
                                )
                            )
                        ).weight(25)
                    )
                    .with(
                        LootTableEntry.method_428(NulliumLootTables.ENDERMAN_OVERWORLD_DESERT).conditionally(
                            LocationCheckLootCondition.builder(
                                LocationPredicate.Builder.create().method_9024(
                                    biomes.getTagOrThrow(ConventionalBiomeTags.IS_DESERT)
                                )
                            )
                        ).weight(25)
                    )
                    .with(
                        LootTableEntry.method_428(NulliumLootTables.ENDERMAN_OVERWORLD_BADLANDS).conditionally(
                            LocationCheckLootCondition.builder(
                                LocationPredicate.Builder.create().method_9024(
                                    biomes.getTagOrThrow(ConventionalBiomeTags.IS_BADLANDS)
                                )
                            )
                        ).weight(25)
                    )

            )
        )
        gen.accept(
            NulliumLootTables.ENDERMAN_OVERWORLD_GENERIC,
            LootTable.builder().pool(
                LootPool.builder().rolls(Utils.constantNum(1))
                    .with(ItemEntry.builder(Items.GRASS_BLOCK).weight(10))
                    .with(ItemEntry.builder(Items.DIRT).weight(3))
                    .with(ItemEntry.builder(Items.POPPY).weight(5))
                    .with(ItemEntry.builder(Items.DANDELION).weight(5))
                    .with(ItemEntry.builder(Items.PUMPKIN).weight(5))
                    .with(ItemEntry.builder(Items.MELON))
                    .with(ItemEntry.builder(Items.STONE).weight(3))
                    .with(ItemEntry.builder(Items.GRAVEL).weight(3))
                    .with(ItemEntry.builder(Items.DEEPSLATE))
                    .with(ItemEntry.builder(Items.RED_MUSHROOM).weight(3))
                    .with(ItemEntry.builder(Items.BROWN_MUSHROOM).weight(3))
            )
        )
        gen.accept(
            NulliumLootTables.ENDERMAN_NETHER_GENERIC,
            LootTable.builder().pool(
                LootPool.builder().rolls(Utils.constantNum(1))
                    .with(ItemEntry.builder(Items.NETHERRACK).weight(10))
                    .with(ItemEntry.builder(Items.CRIMSON_NYLIUM).weight(3))
                    .with(ItemEntry.builder(Items.WARPED_NYLIUM).weight(7))
                    .with(ItemEntry.builder(Items.BLACKSTONE).weight(7))
                    .with(ItemEntry.builder(Items.SOUL_SAND).weight(3))
                    .with(ItemEntry.builder(Items.SOUL_SOIL).weight(3))
                    .with(ItemEntry.builder(Items.BASALT).weight(3))
            )
        )
        gen.accept(
            NulliumLootTables.ENDERMAN_END_GENERIC,
            LootTable.builder().pool(
                LootPool.builder().rolls(Utils.constantNum(1))
                    .with(EmptyEntry.builder().weight(250))
                    .with(ItemEntry.builder(Items.END_STONE).weight(250))
                    .with(ItemEntry.builder(Items.CHORUS_FLOWER).weight(50))
                    .with(ItemEntry.builder(Items.OBSIDIAN))
            )
        )
        gen.accept(
            NulliumLootTables.ENDERMAN_OVERWORLD_FLOWER,
            LootTable.builder().pool(
                LootPool.builder().rolls(Utils.constantNum(1))
                    .with(ItemEntry.builder(Items.POPPY).weight(10))
                    .with(ItemEntry.builder(Items.DANDELION).weight(10))
                    .with(ItemEntry.builder(Items.ALLIUM).weight(10))
                    .with(ItemEntry.builder(Items.AZURE_BLUET).weight(10))
                    .with(ItemEntry.builder(Items.CORNFLOWER).weight(10))
                    .with(ItemEntry.builder(Items.LILY_OF_THE_VALLEY).weight(10))
                    .with(ItemEntry.builder(Items.OXEYE_DAISY).weight(10))
                    .with(ItemEntry.builder(Items.ORANGE_TULIP).weight(10))
                    .with(ItemEntry.builder(Items.PINK_TULIP).weight(10))
                    .with(ItemEntry.builder(Items.RED_TULIP).weight(10))
                    .with(ItemEntry.builder(Items.WHITE_TULIP).weight(10))
                    .with(ItemEntry.builder(Items.BLUE_ORCHID))
            )
        )
        gen.accept(
            NulliumLootTables.ENDERMAN_OVERWORLD_ICE,
            LootTable.builder().pool(
                LootPool.builder().rolls(Utils.constantNum(1))
                    .with(ItemEntry.builder(Items.SNOW_BLOCK).weight(10))
                    .with(ItemEntry.builder(Items.POWDER_SNOW_BUCKET).weight(10))
                    .with(ItemEntry.builder(Items.ICE).weight(10))
                    .with(ItemEntry.builder(Items.PACKED_ICE).weight(10))
                    .with(ItemEntry.builder(Items.BLUE_ICE))
            )
        )
        gen.accept(
            NulliumLootTables.ENDERMAN_OVERWORLD_DESERT,
            LootTable.builder().pool(
                LootPool.builder().rolls(Utils.constantNum(1))
                    .with(ItemEntry.builder(Items.SAND).weight(25))
                    .with(ItemEntry.builder(Items.SANDSTONE).weight(25))
                    .with(ItemEntry.builder(Items.RED_SAND).weight(10))
                    .with(ItemEntry.builder(Items.RED_SANDSTONE).weight(10))
                    .with(ItemEntry.builder(Items.DEAD_BUSH).weight(15))
                    .with(ItemEntry.builder(Items.CACTUS).weight(7))
            )
        )
        gen.accept(
            NulliumLootTables.ENDERMAN_OVERWORLD_BADLANDS,
            LootTable.builder().pool(
                LootPool.builder().rolls(Utils.constantNum(1))
                    .with(ItemEntry.builder(Items.RED_SAND).weight(100))
                    .with(ItemEntry.builder(Items.RED_SANDSTONE).weight(100))
                    .with(ItemEntry.builder(Items.DEAD_BUSH).weight(150))
                    .with(ItemEntry.builder(Items.CACTUS).weight(70))
                    .with(ItemEntry.builder(Items.TERRACOTTA).weight(50))
                    .with(ItemEntry.builder(Items.WHITE_TERRACOTTA).weight(5))
                    .with(ItemEntry.builder(Items.LIGHT_GRAY_TERRACOTTA))
                    .with(ItemEntry.builder(Items.BROWN_TERRACOTTA))
                    .with(ItemEntry.builder(Items.RED_TERRACOTTA))
                    .with(ItemEntry.builder(Items.ORANGE_TERRACOTTA))
                    .with(ItemEntry.builder(Items.YELLOW_TERRACOTTA))
            )
        )
    }
}