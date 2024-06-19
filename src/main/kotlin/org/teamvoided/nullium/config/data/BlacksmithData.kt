package org.teamvoided.nullium.config.data

import kotlinx.serialization.Serializable
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import org.teamvoided.nullium.data.tags.NulliumMaterialTags
import org.teamvoided.nullium.serlializers.IdentifierSerializer
import org.teamvoided.nullium.util.id

@Serializable
data class BlacksmithData(
    val defaultRepairCost: CostData,
    val materialRepairCosts: List<MaterialRepairCosts>,
) {
    constructor() : this(
        CostData(),
        listOf(
            // Tools
            NulliumMaterialTags.WOOD.id tag CostData(1, 0.7),
            NulliumMaterialTags.STONE.id tag CostData(2, 0.8),
            NulliumMaterialTags.GOLD.id tag CostData(1, 0.5),
            NulliumMaterialTags.IRON.id tag CostData(3, 0.9),
            NulliumMaterialTags.DIAMOND.id tag CostData(5, 1.0),
            NulliumMaterialTags.NETHERITE.id tag CostData(7, 1.1),
            // Armor
            NulliumMaterialTags.LEATHER.id tag CostData(1, 0.6),
            NulliumMaterialTags.CHAIN.id tag CostData(2, 0.7),
            // Items
            Items.MACE.id() item CostData(10, 1.6),
            Items.TRIDENT.id() item CostData(4, 2.0),
            Items.BOW.id() item CostData(4, 1.1),
            Items.CROSSBOW.id() item CostData(4, 1.1),
            Items.SHIELD.id() item CostData(1, 1.5),
            Items.ELYTRA.id() item CostData(7, 2.0),
            Items.SHEARS.id() item CostData(1, 0.4),
            Items.FISHING_ROD.id() item CostData(2, 1.1),
        ),
    )
}

@Serializable
data class MaterialRepairCosts(
    val type: IdentifierType,
    @Serializable(with = IdentifierSerializer::class) val id: Identifier,
    val data: CostData
)

infix fun Identifier.item(costData: CostData): MaterialRepairCosts =
    MaterialRepairCosts(IdentifierType.ITEM, this, costData)

infix fun Identifier.tag(costData: CostData): MaterialRepairCosts =
    MaterialRepairCosts(IdentifierType.TAG, this, costData)

@Serializable
data class CostData(
    @JvmField val baseCost: Int, @JvmField val enchantmentMultiplier: Double
) {
    constructor() : this(1, 1.0)
}

