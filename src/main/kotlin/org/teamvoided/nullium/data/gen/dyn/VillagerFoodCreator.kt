package org.teamvoided.nullium.data.gen.dyn

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.registry.BootstrapContext
import net.minecraft.registry.HolderSet
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import org.teamvoided.nullium.data.nullium.NulVillagerFoods
import org.teamvoided.nullium.data.nullium.data.VillagerFood

object VillagerFoodCreator {
    fun bootstrap(c: BootstrapContext<VillagerFood>) {
        c.make(NulVillagerFoods.BREAD, Items.BREAD, 4)
        c.make(NulVillagerFoods.POTATO, Items.POTATO, 1)
        c.make(NulVillagerFoods.CARROT, Items.CARROT, 1)
        c.make(NulVillagerFoods.BEETROOT, Items.BEETROOT, 1)


        c.make(NulVillagerFoods.PIES, ConventionalItemTags.PIE_FOODS, 4)

    }

    @Suppress("DEPRECATION")
    fun BootstrapContext<VillagerFood>.make(key: RegistryKey<VillagerFood>, item: Item, amount: Int) {
        this.register(key, VillagerFood(HolderSet.createDirect(item.builtInRegistryHolder), amount))
    }

    fun BootstrapContext<VillagerFood>.make(key: RegistryKey<VillagerFood>, itemTag: TagKey<Item>, amount: Int) {
        this.register(key, VillagerFood(this.getRegistryLookup(RegistryKeys.ITEM).getTagOrThrow(itemTag), amount))
    }
}