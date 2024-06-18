package org.teamvoided.nullium.config

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import net.fabricmc.loader.api.FabricLoader
import org.teamvoided.nullium.Nullium.JSON
import org.teamvoided.nullium.Nullium.MODID
import org.teamvoided.nullium.config.module.MobScaleCfg
import org.teamvoided.nullium.config.module.BlacksmithCfg
import org.teamvoided.nullium.config.module.MiscellaneousCfg
import org.teamvoided.nullium.config.module.SwitchboardCfg
import java.io.File
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

@Suppress("TooGenericExceptionCaught", "SwallowedException")
object NulConfigManager {
    val configDir: Path = FabricLoader.getInstance().configDir.resolve(MODID)

    private val infoFile = configDir.resolve("internal_info")
    private var info = Info()
    fun info(): Info = info.copy()

    const val CONFIG_VERSION = 1.0
    val switchboard = SwitchboardCfg()


    lateinit var miscellaneous: MiscellaneousCfg private set
    lateinit var bigSalmon: MobScaleCfg private set
    lateinit var blacksmith: BlacksmithCfg private set


    fun init() {
        if (!configDir.exists()) configDir.createDirectories()

        miscellaneous = MiscellaneousCfg()
        bigSalmon = MobScaleCfg()
        blacksmith = BlacksmithCfg()
        loadAll()
    }

    @JvmStatic
    fun loadSwitch() {
        if (!configDir.exists()) configDir.createDirectories()
        switchboard.load()
    }


    fun loadAll(): Int {
        load()
        switchboard.load()

        return listOf(
            bigSalmon.load(),
            blacksmith.load(),
            miscellaneous.load()
        ).count { !it }
    }

    private fun load() {
        if (!infoFile.exists()) {
            save()
        } else {
            try {
                info = infoFile.readText().let { JSON.decodeFromString(it) }
                if (info.version != CONFIG_VERSION) {
                    info = Info()
                    save()
                }
            } catch (e: Exception) {
                // Prevent crashing on invalid file
            }
        }
    }

    private fun save() {
        try {
            infoFile.writeText(JSON.encodeToString(info))
        } catch (e: Exception) {
            // Prevent crashing on invalid file
        }
    }

    @Serializable
    data class Info(val comment: String, val version: Double) {
        constructor(version: Double) : this("DONT TOUCH! Internal config file! Will break if you do!", version)
        constructor() : this(CONFIG_VERSION)
    }

    fun getSubCfgFile(name: String): File = configDir.resolve(name).toFile()
}
