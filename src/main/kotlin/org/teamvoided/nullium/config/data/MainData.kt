package org.teamvoided.nullium.config.data

import kotlinx.serialization.Serializable

@Serializable
data class MainData(
    val info: String,
    val stoppingInfo: String,
    @JvmField
    val stopping: Stopping,
    val reloadableInfo: String,
    @JvmField
    val reloadable: Reloadable
) {

    constructor() : this(
        "Hello I'm the main config!",
        "If you change a value the Stopping category, you will need to restart your game (or server).",
        Stopping(),
        "If you change anything in Reloadable category, you will need to run /reload",
        Reloadable()
    )

    @Serializable
    data class Stopping(
        val enableMobScale: Boolean,
        @JvmField
        val enableHolderman: Boolean,
        @JvmField
        val enableBlacksmith: Boolean,
        val enableStackablePotions: Boolean,
        @JvmField
        val enableGlowBerriesGlow: Boolean,
    ) {
        constructor() : this(
            true,
            true,
            true,
            true,
            true
        )
    }

    @Serializable
    data class Reloadable(
        val cakeDrops: Boolean,
        val barterUpgrades: Boolean,
    ) {
        constructor() : this(true, true)
    }
}