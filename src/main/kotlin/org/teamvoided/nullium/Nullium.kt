package org.teamvoided.nullium

import kotlinx.serialization.json.Json
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.teamvoided.nullium.config.ConfigManager
import org.teamvoided.nullium.init.registerCommands
import org.teamvoided.nullium.module.bigSalmon

@Suppress("unused")
object Nullium {
    const val MODID = "nullium"
    val JSON = Json { prettyPrint = true }

    @JvmField
    val log: Logger = LoggerFactory.getLogger(Nullium::class.simpleName)

    fun commonInit() {
        log.info("Nulls your ium .?.")
        ConfigManager
        registerCommands()
        bigSalmon()
    }

    fun id(path: String) = Identifier(MODID, path)
}
