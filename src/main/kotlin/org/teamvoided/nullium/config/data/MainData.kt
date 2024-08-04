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
        RELOADABLE_DEFAULT
    )

    constructor(data1: MainData1_1) : this() {
        stopping["stackablePotions"] = data1.stopping.enableStackablePotions
        stopping["mobScale"] = data1.stopping.enableMobScale
        stopping["holderman"] = data1.stopping.enableHolderman
        stopping["blacksmith"] = data1.stopping.enableBlacksmith
        stopping["glowBerriesGlow"] = data1.stopping.enableGlowBerriesGlow
        stopping["copperBulbRevert"] = data1.stopping.enableCopperBulbRevert
        stopping["compostable"] = data1.stopping.enableCompostable

        reloadable["cakeDrops"] = data1.reloadable.cakeDrops
        reloadable["barterUpgrades"] = data1.reloadable.barterUpgrades
    }

    // 1.1 options
    fun enableStackablePotions() = stopping["stackablePotions"] ?: handleMissingStopping("stackablePotions")
    fun enableMobScale() = stopping["mobScale"] ?: handleMissingStopping("mobScale")
    fun enableHolderman() = stopping["holderman"] ?: handleMissingStopping("holderman")
    fun enableBlacksmith() = stopping["blacksmith"] ?: handleMissingStopping("blacksmith")
    fun enableGlowBerriesGlow() = stopping["glowBerriesGlow"] ?: handleMissingStopping("glowBerriesGlow")
    fun enableCopperBulbRevert() = stopping["copperBulbRevert"] ?: handleMissingStopping("copperBulbRevert")
    fun enableCompostable() = stopping["compostable"] ?: handleMissingStopping("compostable")

    // 1.2 options
    fun enableStackableSaddles() = stopping["stackableSaddles"] ?: handleMissingStopping("stackableSaddles")
    fun enableStackableHorseArmor() = stopping["stackableHorseArmor"] ?: handleMissingStopping("stackableHorseArmor")
    fun enableStackableMusicDiscs() = stopping["stackableMusicDiscs"] ?: handleMissingStopping("stackableMusicDiscs")
    fun enableStackableMinecarts() = stopping["stackableMinecarts"] ?: handleMissingStopping("stackableMinecarts")


    // 1.1 options
    fun getCakeDrops() = reloadable["cakeDrops"] ?: handleMissingReloadable("cakeDrops")
    fun getBarterUpgrades() = reloadable["barterUpgrades"] ?: handleMissingReloadable("barterUpgrades")


    private fun handleMissingStopping(name: String): Boolean {
        log.warn("Missing option $name in Stopping category. Using default value and saving config.")
        val default = STOPPING_DEFAULT[name] ?: throw Error("Missing default value for config option $name")
        stopping[name] = default
        NulConfigManager.main.save()
        return true
    }

    private fun handleMissingReloadable(name: String): Boolean {
        log.warn("Missing option $name in Reloadable category. Using default value and saving config.")
        val default = RELOADABLE_DEFAULT[name] ?: throw Error("Missing default value for config option $name")
        reloadable[name] = default
        NulConfigManager.main.save()
        return true
    }
    companion object {
        val STOPPING_DEFAULT = mutableMapOf(
            // 1.1 options
            "stackablePotions" to true,
            "mobScale" to true,
            "holderman" to true,
            "blacksmith" to true,
            "glowBerriesGlow" to true,
            "copperBulbRevert" to true,
            "compostable" to true,
            // 1.2 options
            "stackableSaddles" to true,
            "stackableHorseArmor" to true,
            "stackableMusicDiscs" to true,
            "stackableMinecarts" to true,
        )
        val RELOADABLE_DEFAULT = mutableMapOf(
            // 1.1 options
            "cakeDrops" to true,
            "barterUpgrades" to true
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
    val reloadable: Reloadable1_1
) {

//    constructor() : this(
//        "Hello I'm the main config!",
//        "If you change a value the Stopping category, you will need to restart your game (or server).",
//        Stopping1_1(),
//        "If you change anything in Reloadable category, you will need to run /reload",
//        Reloadable1_1()
//    )

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
        val enableCompostable: Boolean
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