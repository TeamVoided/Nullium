package org.teamvoided.nullium.config.data

import kotlinx.serialization.Serializable
import org.teamvoided.nullium.Nullium.log
import org.teamvoided.nullium.config.NulConfigManager

@Serializable
data class MainData(
    val info: String,
    val stoppingInfo: String,
    private val stopping: MutableMap<String, Boolean>,
    val reloadableInfo: String,
    private val reloadable: MutableMap<String, Boolean>,
) {
    constructor() : this(
        "Hello I'm the main config!",
        "If you change a value the Stopping category, you will need to restart your game (or server).",
        STOPPING_DEFAULT,
        "If you change anything in Reloadable category, you will need to run /reload",
        mutableMapOf()
    )

    constructor(data1: MainData1_1) : this() {
        stopping["blacksmith"] = data1.stopping.enableBlacksmith
    }

    fun enableBlacksmith() = stopping["blacksmith"] ?: handleMissingStopping("blacksmith")

    private fun handleMissingStopping(name: String): Boolean {
        log.warn("Missing option $name in Stopping category. Using default value and saving config.")
        val default = STOPPING_DEFAULT[name] ?: throw Error("Missing default value for config option $name")
        stopping[name] = default
        NulConfigManager.main.save()
        return true
    }

    companion object {
        val STOPPING_DEFAULT = mutableMapOf(
            // 1.1 options
            "blacksmith" to true,
        )
    }
}

@Serializable
data class MainData1_1(
    val info: String,
    val stoppingInfo: String,
    @JvmField
    val stopping: Stopping1_1,
    val reloadableInfo: String,
    @JvmField
    val reloadable: Reloadable1_1,
) {
    @Serializable
    data class Stopping1_1(
        val enableStackablePotions: Boolean,
        val enableMobScale: Boolean,
        @JvmField
        val enableHolderman: Boolean,
        @JvmField
        val enableBlacksmith: Boolean,
        @JvmField
        val enableGlowBerriesGlow: Boolean,
        @JvmField
        val enableCopperBulbRevert: Boolean,
        val enableCompostable: Boolean,
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

    @Serializable
    data class Reloadable1_1(
        val cakeDrops: Boolean,
        val barterUpgrades: Boolean,
    ) {
        constructor() : this(true, true)
    }
}