package org.teamvoided.nullium.data.gen.loot

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags
import net.minecraft.item.Items
import net.minecraft.loot.LootTable
import net.minecraft.loot.context.LootContextTypes
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import org.teamvoided.nullium.data.loot.NulliumLootTables
import org.teamvoided.nullium.util.lootTable
import java.util.concurrent.CompletableFuture
import java.util.function.BiConsumer

class EntityLootTablesProvider(o: FabricDataOutput, val r: CompletableFuture<HolderLookup.Provider>) :
    SimpleFabricLootTableProvider(o, r, LootContextTypes.ENTITY) {
    override fun generate(gen: BiConsumer<RegistryKey<LootTable>, LootTable.Builder>) {
        val biomes = r.get().getLookupOrThrow(RegistryKeys.BIOME)
        gen.accept(NulliumLootTables.ENDERMAN_HOLDS,
            lootTable {
                pool {
                    rolls(1)
                    empty(1000)
                    lootTable(NulliumLootTables.ENDERMAN_OVERWORLD_GENERIC, 25)
                    lootTable(NulliumLootTables.ENDERMAN_NETHER_GENERIC) { biomes.biomeTagCheck(ConventionalBiomeTags.IS_NETHER) }
                    lootTable(NulliumLootTables.ENDERMAN_END_GENERIC) { biomes.biomeTagCheck(ConventionalBiomeTags.IS_END) }
                    lootTable(NulliumLootTables.ENDERMAN_OVERWORLD_FLOWER) {
                        weight(50)
                        biomes.biomeTagCheck(ConventionalBiomeTags.IS_FLORAL)
                    }
                    lootTable(NulliumLootTables.ENDERMAN_OVERWORLD_ICE) {
                        weight(25)
                        biomes.biomeTagCheck(ConventionalBiomeTags.IS_COLD_OVERWORLD)
                    }
                    lootTable(NulliumLootTables.ENDERMAN_OVERWORLD_DESERT) {
                        weight(25)
                        biomes.biomeTagCheck(ConventionalBiomeTags.IS_DESERT)
                    }
                    lootTable(NulliumLootTables.ENDERMAN_OVERWORLD_BADLANDS) {
                        weight(25)
                        biomes.biomeTagCheck(ConventionalBiomeTags.IS_BADLANDS)
                    }
                }
            }
        )

        gen.accept(NulliumLootTables.ENDERMAN_OVERWORLD_GENERIC,
            lootTable {
                pool {
                    rolls(1)
                    item(Items.GRASS_BLOCK, 10)
                    item(Items.DIRT, 3)
                    item(Items.POPPY, 5)
                    item(Items.DANDELION, 5)
                    item(Items.PUMPKIN, 5)
                    item(Items.MELON)
                    item(Items.STONE, 3)
                    item(Items.GRAVEL, 3)
                    item(Items.DEEPSLATE)
                    item(Items.RED_MUSHROOM, 3)
                    item(Items.BROWN_MUSHROOM, 3)
                }
            }
        )

        gen.accept(
            NulliumLootTables.ENDERMAN_NETHER_GENERIC,
            lootTable {
                pool {
                    rolls(1)
                    item(Items.NETHERRACK, 10)
                    item(Items.CRIMSON_NYLIUM, 3)
                    item(Items.WARPED_NYLIUM, 7)
                    item(Items.BLACKSTONE, 7)
                    item(Items.SOUL_SAND, 3)
                    item(Items.SOUL_SOIL, 3)
                    item(Items.BASALT, 3)
                }
            }
        )

        gen.accept(NulliumLootTables.ENDERMAN_END_GENERIC,
            lootTable {
                pool {
                    rolls(1)
                    item(Items.END_STONE, 250)
                    item(Items.CHORUS_FLOWER, 50)
                    item(Items.OBSIDIAN)
                }
            }
        )

        gen.accept(NulliumLootTables.ENDERMAN_OVERWORLD_FLOWER,
            lootTable {
                pool {
                    rolls(1)
                    item(Items.POPPY, 10)
                    item(Items.DANDELION, 10)
                    item(Items.ALLIUM, 10)
                    item(Items.AZURE_BLUET, 10)
                    item(Items.CORNFLOWER, 10)
                    item(Items.LILY_OF_THE_VALLEY, 10)
                    item(Items.OXEYE_DAISY, 10)
                    item(Items.ORANGE_TULIP, 10)
                    item(Items.PINK_TULIP, 10)
                    item(Items.RED_TULIP, 10)
                    item(Items.WHITE_TULIP, 10)
                    item(Items.BLUE_ORCHID)
                }
            }
        )

        gen.accept(
            NulliumLootTables.ENDERMAN_OVERWORLD_ICE,
            lootTable {
                pool {
                    rolls(1)
                    item(Items.SNOW_BLOCK, 10)
                    item(Items.POWDER_SNOW_BUCKET, 10)
                    item(Items.ICE, 10)
                    item(Items.PACKED_ICE, 10)
                    item(Items.BLUE_ICE)
                }
            }
        )

        gen.accept(
            NulliumLootTables.ENDERMAN_OVERWORLD_DESERT,
            lootTable {
                pool {
                    rolls(1)
                    item(Items.SAND, 25)
                    item(Items.SANDSTONE, 25)
                    item(Items.RED_SAND, 10)
                    item(Items.RED_SANDSTONE, 10)
                    item(Items.DEAD_BUSH, 15)
                    item(Items.CACTUS, 7)
                }
            }
        )

        gen.accept(
            NulliumLootTables.ENDERMAN_OVERWORLD_BADLANDS,
            lootTable {
                pool {
                    rolls(1)
                    item(Items.RED_SAND, 100)
                    item(Items.RED_SANDSTONE, 100)
                    item(Items.DEAD_BUSH, 150)
                    item(Items.CACTUS, 70)
                    item(Items.TERRACOTTA, 50)
                    item(Items.WHITE_TERRACOTTA, 5)
                    item(Items.LIGHT_GRAY_TERRACOTTA)
                    item(Items.BROWN_TERRACOTTA)
                    item(Items.RED_TERRACOTTA)
                    item(Items.ORANGE_TERRACOTTA)
                    item(Items.YELLOW_TERRACOTTA)

                }
            }
        )
    }
}