package org.teamvoided.nullium.config.data

import kotlinx.serialization.Serializable

@Serializable
data class MainData(
    val enableMobScale: Boolean,
    @JvmField
    val enableHolderman: Boolean,
    @JvmField
    val enableBlacksmith: Boolean,
    val enableStackablePotions: Boolean,
    val cakeDrops: Boolean,
    val barterUpgrades: Boolean,
    @JvmField
    val enableGlowBerriesGlow: Boolean,
) {

    constructor() : this(
        true,
        true,
        true,
        true,
        true,
        true,
        true
    )
}