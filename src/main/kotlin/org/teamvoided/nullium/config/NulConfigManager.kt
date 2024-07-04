package org.teamvoided.nullium.config

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import net.fabricmc.loader.api.FabricLoader
import org.teamvoided.nullium.Nullium.JSON
import org.teamvoided.nullium.Nullium.MODID
import org.teamvoided.nullium.Nullium.log
import org.teamvoided.nullium.config.module.*
import org.teamvoided.nullium.util.getTimeFileName
import java.io.File
import java.nio.file.Path
import kotlin.io.path.*

@Suppress("TooGenericExceptionCaught", "SwallowedException")
object NulConfigManager {
    val configDir: Path = FabricLoader.getInstance().configDir.resolve(MODID)
    val backupDir: Path = FabricLoader.getInstance().configDir.resolve(MODID).resolve("backup")

    private val infoFile = configDir.resolve("internal_info")
    var info = Info()
        private set

    const val CONFIG_VERSION = 1.1

    @JvmStatic
    val main = MainCfg()

    val bigSalmon: MobScaleCfg by lazy { MobScaleCfg() }
    val blacksmith: BlacksmithCfg by lazy { BlacksmithCfg() }
    val compostable: CompostableCfg by lazy { CompostableCfg() }

//    val miscellaneous: MiscellaneousCfg by lazy { MiscellaneousCfg() }

    fun init() {
        if (!configDir.exists()) configDir.createDirectories()
        loadAll()
    }

    @JvmStatic
    fun loadMain() {
        if (!configDir.exists()) configDir.createDirectories()
        loadInfo()

        main.load()
    }


    fun loadAll(): Int {
        loadInfo()
        main.load()

        return listOf(
            bigSalmon.load(),
            blacksmith.load(),
            compostable.load(),
//            miscellaneous.load()
        ).count { !it }
    }

    private fun backupAndClear() {
        if (!backupDir.exists()) backupDir.createDirectories()

        var datedBackup = backupDir.resolve(getTimeFileName())
        if (!datedBackup.exists()) datedBackup.createDirectories()
        else {
            datedBackup = backupDir.resolve(datedBackup.name + "-1")
            datedBackup.createDirectories()
        }
        configDir.toFile().listFiles()?.forEach { file ->
            file.renameTo(datedBackup.resolve(file.name).toFile())
        }
    }

    private fun loadInfo() {
        if (!infoFile.exists()) {
            save()
        } else {
            try {
                info = infoFile.readText().let { JSON.decodeFromString(it) }
                if (info.version != CONFIG_VERSION) {
                    log.error(
                        buildString {
                            append("\n")
                            append("\tCurrent config versions is [$CONFIG_VERSION], version loaded from file [${info.version}]\n")
                            append("\tConfig version dose not match the version the mod supports!\n")
                            append("\tYour config will be backed up and new config will be generated.")
                        }
                    )
                    backupAndClear()

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
