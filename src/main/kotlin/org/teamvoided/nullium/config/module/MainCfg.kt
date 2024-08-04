package org.teamvoided.nullium.config.module

import org.teamvoided.nullium.Nullium.JSON
import org.teamvoided.nullium.Nullium.log
import org.teamvoided.nullium.config.ConfigInstance
import org.teamvoided.nullium.config.NulConfigManager
import org.teamvoided.nullium.config.data.MainData
import org.teamvoided.nullium.config.data.MainData1_1

class MainCfg : ConfigInstance<MainData> {
    override val name: String = "Main"
    override var data = MainData()
    override fun serializer() = MainData.serializer()

    override fun load(): Boolean {
        return if (!configFile.exists()) {
            log.warn("$name config file not found, creating default!")
            save()
            false
        } else {
            try {
                val cfgVersion = NulConfigManager.getOldCfgVersion()
                if (cfgVersion == null) {
                    data = configFile.readText().let { JSON.decodeFromString(it) }
                } else if (cfgVersion == 1.1) {
                    data = MainData(configFile.readText().let { JSON.decodeFromString(MainData1_1.serializer(), it) })
                    save()
                }
            } catch (e: IllegalArgumentException) {
                log.error("Failed to load $name config file", e)
                return false
            }
            log.info("Loaded $name config file")
            true
        }
    }
}
