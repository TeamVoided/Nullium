package org.teamvoided.nullium.data.gen.dyn

import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.registry.*
import net.minecraft.registry.tag.TagKey
import org.teamvoided.nullium.data.gen.dyn.RepairDataCreator.make
import org.teamvoided.nullium.data.nullium.NulRepairData
import org.teamvoided.nullium.data.nullium.data.RepairData
import org.teamvoided.nullium.data.tags.NulliumMaterialTags

object RepairDataCreator {
    fun bootstrap(c: BootstrapContext<RepairData>) {
        // Materials
        c.make(NulRepairData.WOOD, NulliumMaterialTags.WOOD, 1, 0.25)
        c.make(NulRepairData.STONE, NulliumMaterialTags.STONE, 1, 0.3)
        c.make(NulRepairData.GOLD, NulliumMaterialTags.GOLD, 1, 0.2)
        c.make(NulRepairData.IRON, NulliumMaterialTags.IRON, 2, 0.45)
        c.make(NulRepairData.DIAMOND, NulliumMaterialTags.DIAMOND, 3, 0.7)
        c.make(NulRepairData.NETHERITE, NulliumMaterialTags.NETHERITE, 4, 0.9)
        c.make(NulRepairData.LEATHER, NulliumMaterialTags.LEATHER, 1, 0.1)
        c.make(NulRepairData.CHAIN, NulliumMaterialTags.CHAIN, 1, 0.2)
        // Items
        c.make(NulRepairData.MACE, Items.MACE, 6, 0.8)
        c.make(NulRepairData.TRIDENT, Items.TRIDENT, 3, 0.6)
        c.make(NulRepairData.BOW, Items.BOW, 2, 0.6)
        c.make(NulRepairData.CROSSBOW, Items.CROSSBOW, 2, 0.6)
        c.make(NulRepairData.SHIELD, Items.SHIELD, 1, 0.4)
        c.make(NulRepairData.ELYTRA, Items.ELYTRA, 6, 0.8)
        c.make(NulRepairData.SHEARS, Items.SHEARS, 1, 0.1)
        c.make(NulRepairData.FISHING_ROD, Items.FISHING_ROD, 2, 0.4)

    }

    @Suppress("DEPRECATION")
    fun BootstrapContext<RepairData>.make(key: RegistryKey<RepairData>, item: Item, cost: Int, multiplier: Double) =
        create(key, RepairData(HolderSet.createDirect(item.builtInRegistryHolder), cost, multiplier))

    fun BootstrapContext<RepairData>.make(
        key: RegistryKey<RepairData>, tag: TagKey<Item>, cost: Int, multiplier: Double,
    ) = create(key, RepairData(getRegistryLookup(RegistryKeys.ITEM).getTagOrThrow(tag), cost, multiplier))


    fun BootstrapContext<RepairData>.create(key: RegistryKey<RepairData>, data: RepairData)
            : Holder.Reference<RepairData> = register(key, data)
}