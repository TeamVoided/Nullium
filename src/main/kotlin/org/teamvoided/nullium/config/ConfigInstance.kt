package org.teamvoided.nullium.config

import kotlinx.serialization.KSerializer
import org.apache.logging.log4j.util.InternalApi
import org.teamvoided.nullium.Nullium.JSON
import org.teamvoided.nullium.Nullium.log
import java.io.File

interface ConfigInstance<T> {
    val name: String
    val configFile: File
        get() = NulConfigManager.getSubCfgFile("${name.lowercase()}.json")

    @InternalApi
    var data: T
    fun data(): T = data

    fun serializer(): KSerializer<T>?

    fun load(): Boolean {
        return if (!configFile.exists()) {
            log.warn("Blacksmith config file not found, creating default!")
            save()
            false
        } else {
            val imported: T
            try {
                if (serializer() == null) throw Error("No serializer for $name")
                imported = configFile.readText().let { JSON.decodeFromString(serializer()!!, it) }
            } catch (e: IllegalArgumentException) {
                log.error("Failed to load Blacksmith config file", e)
                return false
            }
            data = imported
            log.info("Loaded Blacksmith config file")
            true
        }
    }

    fun save(): Boolean {
        try {
            if (serializer() == null) throw Error("No serializer for $name")
            configFile.writeText(JSON.encodeToString(serializer()!!, data))
        } catch (e: IllegalArgumentException) {
            log.error("Failed to save $name config file", e)
            return false
        }
        return true
    }
}