package org.teamvoided.nullium.data.gen.tags

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.item.Items
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.tag.ItemTags
import org.teamvoided.nullium.data.tags.NulliumMaterialTags
import java.util.concurrent.CompletableFuture

class ItemTagProvider(output: FabricDataOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>) :
    FabricTagProvider.ItemTagProvider(output, registriesFuture) {
    override fun configure(arg: HolderLookup.Provider) {
        materialTags()
        repairMaterialTags()
    }

    private fun materialTags() {
        getOrCreateTagBuilder(NulliumMaterialTags.NETHERITE)
            .add(
                Items.NETHERITE_SWORD,
                Items.NETHERITE_PICKAXE,
                Items.NETHERITE_AXE,
                Items.NETHERITE_SHOVEL,
                Items.NETHERITE_HOE,

                Items.NETHERITE_HELMET,
                Items.NETHERITE_CHESTPLATE,
                Items.NETHERITE_LEGGINGS,
                Items.NETHERITE_BOOTS
            )
        getOrCreateTagBuilder(NulliumMaterialTags.DIAMOND)
            .add(
                Items.DIAMOND_SWORD,
                Items.DIAMOND_PICKAXE,
                Items.DIAMOND_AXE,
                Items.DIAMOND_SHOVEL,
                Items.DIAMOND_HOE,

                Items.DIAMOND_HELMET,
                Items.DIAMOND_CHESTPLATE,
                Items.DIAMOND_LEGGINGS,
                Items.DIAMOND_BOOTS
            )
        getOrCreateTagBuilder(NulliumMaterialTags.IRON)
            .add(
                Items.IRON_SWORD,
                Items.IRON_PICKAXE,
                Items.IRON_AXE,
                Items.IRON_SHOVEL,
                Items.IRON_HOE,

                Items.IRON_HELMET,
                Items.IRON_CHESTPLATE,
                Items.IRON_LEGGINGS,
                Items.IRON_BOOTS
            )
        getOrCreateTagBuilder(NulliumMaterialTags.GOLD)
            .add(
                Items.GOLDEN_SWORD,
                Items.GOLDEN_PICKAXE,
                Items.GOLDEN_AXE,
                Items.GOLDEN_SHOVEL,
                Items.GOLDEN_HOE,

                Items.GOLDEN_HELMET,
                Items.GOLDEN_CHESTPLATE,
                Items.GOLDEN_LEGGINGS,
                Items.GOLDEN_BOOTS
            )
        getOrCreateTagBuilder(NulliumMaterialTags.STONE)
            .add(
                Items.STONE_SWORD,
                Items.STONE_PICKAXE,
                Items.STONE_AXE,
                Items.STONE_SHOVEL,
                Items.STONE_HOE,
            )
        getOrCreateTagBuilder(NulliumMaterialTags.WOOD)
            .add(
                Items.WOODEN_SWORD,
                Items.WOODEN_PICKAXE,
                Items.WOODEN_AXE,
                Items.WOODEN_SHOVEL,
                Items.WOODEN_HOE,
            )

        getOrCreateTagBuilder(NulliumMaterialTags.CHAIN)
            .add(
                Items.CHAINMAIL_HELMET,
                Items.CHAINMAIL_CHESTPLATE,
                Items.CHAINMAIL_LEGGINGS,
                Items.CHAINMAIL_BOOTS,
            )
        getOrCreateTagBuilder(NulliumMaterialTags.LEATHER)
            .add(
                Items.LEATHER_HELMET,
                Items.LEATHER_CHESTPLATE,
                Items.LEATHER_LEGGINGS,
                Items.LEATHER_BOOTS,
            )
    }

    private fun repairMaterialTags() {
        getOrCreateTagBuilder(NulliumMaterialTags.REPAIR_NETHERITE)
            .add(Items.NETHERITE_SCRAP)
        getOrCreateTagBuilder(NulliumMaterialTags.REPAIR_DIAMOND)
            .add(Items.DIAMOND)
        getOrCreateTagBuilder(NulliumMaterialTags.REPAIR_IRON)
            .add(Items.IRON_INGOT)
        getOrCreateTagBuilder(NulliumMaterialTags.REPAIR_GOLD)
            .add(Items.GOLD_INGOT)
        getOrCreateTagBuilder(NulliumMaterialTags.REPAIR_STONE)
            .forceAddTag(ItemTags.STONE_TOOL_MATERIALS)
        getOrCreateTagBuilder(NulliumMaterialTags.REPAIR_WOOD)
            .forceAddTag(ItemTags.PLANKS)
        getOrCreateTagBuilder(NulliumMaterialTags.REPAIR_CHAIN)
            .add(Items.CHAIN)
        getOrCreateTagBuilder(NulliumMaterialTags.REPAIR_LEATHER)
            .add(Items.LEATHER)
    }
}
