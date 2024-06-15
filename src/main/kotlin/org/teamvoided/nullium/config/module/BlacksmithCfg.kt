package org.teamvoided.nullium.config.module

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import org.teamvoided.nullium.Nullium.JSON
import org.teamvoided.nullium.config.ConfigInstance
import org.teamvoided.nullium.config.NulConfigManager
import org.teamvoided.nullium.data.NulliumMaterialTags
import org.teamvoided.nullium.serlializers.IdentifierSerializer
import org.teamvoided.nullium.util.id
import java.io.File

class BlacksmithCfg : ConfigInstance<BlacksmithCfg.Companion.BlacksmithData> {
    override val configFile: File = NulConfigManager.getSubCfgFile("blacksmith.json")
    private var data = BlacksmithData()
    override fun data(): BlacksmithData = data

    override fun load(): Boolean {
        data = BlacksmithData()
        configFile.writeText(JSON.encodeToString(data))
        return true
    }

    override fun save(): Boolean {
        return true
    }


    companion object {
        @Serializable
        data class BlacksmithData(
            @JvmField
            val enable: Boolean,
            @JvmField
            val materialRepairCosts: Map<@Serializable(with = IdentifierSerializer::class) Identifier, CostData>,
            @JvmField
            val defaultRepairCost: CostData,
        ) {
            constructor() : this(
                true,
                mapOf(
                    // Tools
                    NulliumMaterialTags.WOOD.id to CostData(1, 0.7),
                    NulliumMaterialTags.STONE.id to CostData(2, 0.8),
                    NulliumMaterialTags.GOLD.id to CostData(1, 0.5),
                    NulliumMaterialTags.IRON.id to CostData(3, 0.9),
                    NulliumMaterialTags.DIAMOND.id to CostData(5, 1.0),
                    NulliumMaterialTags.NETHERITE.id to CostData(7, 1.1),
                    // Armor
                    NulliumMaterialTags.LEATHER.id to CostData(1, 0.6),
                    NulliumMaterialTags.CHAIN.id to CostData(2, 0.7),
                    // Items
                    Items.MACE.id() to CostData(10, 1.6),
                    Items.TRIDENT.id() to CostData(4, 2.0),
                    Items.BOW.id() to CostData(4, 1.1),
                    Items.CROSSBOW.id() to CostData(4, 1.1),
                    Items.SHIELD.id() to CostData(1, 1.5),
                    Items.ELYTRA.id() to CostData(7, 2.0),
                    Items.SHEARS.id() to CostData(1, 0.4),
                    Items.FISHING_ROD.id() to CostData(2, 1.1),
                ),
                CostData()
            )
        }

        @Serializable
        data class CostData(
            @JvmField
            val baseCost: Int,
            @JvmField
            val enchantmentMultiplier: Double
        ) {
            constructor() : this(1, 1.0)
        }
    }
}
