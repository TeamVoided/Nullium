package org.teamvoided.nullium.config.data

import kotlinx.serialization.Serializable
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import org.teamvoided.nullium.serlializers.IdentifierSerializer
import org.teamvoided.nullium.util.id

@Serializable
data class CompostableData(val compostEntries: List<Entry>) {
    constructor() : this(
        listOf(
            Entry(Items.GOLDEN_APPLE.id(), 1f),
            Entry(Items.GOLDEN_CARROT.id(), 1f)
        )
    )

    @Serializable
    data class Entry(
        val item: @Serializable(with = IdentifierSerializer::class) Identifier,
        val layerChance: Float
    )
}
