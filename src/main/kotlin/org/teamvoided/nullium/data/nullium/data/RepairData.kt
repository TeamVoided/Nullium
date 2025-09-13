package org.teamvoided.nullium.data.nullium.data

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.HolderSet
import net.minecraft.registry.RegistryCodecs
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.dynamic.Codecs
import net.minecraft.world.World
import org.teamvoided.nullium.Nullium.CONFIG
import org.teamvoided.nullium.init.NulRegistryKeys.REPAIR_DATA

data class RepairData(
    val items: HolderSet<Item>,
    @JvmField val baseCost: Int,
    @JvmField val enchantmentMultiplier: Double,
) {

    companion object {
        fun World.getRepairData(item: ItemStack): RepairData? {
            val reg = registryManager.get(REPAIR_DATA)
            for ((_, entry) in reg.entries) {
                if (item.isIn(entry.items)) return entry
            }

            return null
        }

        fun dataFromConfig() =
            RepairData(HolderSet.empty(), CONFIG.defaultBaseCost.get(), CONFIG.defaultEnchantmentMultiplier.get())

        val CODEC: Codec<RepairData> = RecordCodecBuilder.create<RepairData> { instance ->
            instance.group(
                RegistryCodecs.homogeneousList<Item>(RegistryKeys.ITEM).fieldOf("items").forGetter(RepairData::items),
                Codecs.NONNEGATIVE_INT.fieldOf("base_cost").forGetter(RepairData::baseCost),
                Codec.DOUBLE.fieldOf("enchantment_multiplier").forGetter(RepairData::enchantmentMultiplier),
            ).apply(instance, ::RepairData)
        }
    }
}


