package org.teamvoided.nullium

import kotlinx.serialization.json.Json
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.teamvoided.nullium.config.NulConfigManager
import org.teamvoided.nullium.init.NulCommandRegistry
import org.teamvoided.nullium.module.BigSalmon

@Suppress("unused")
object Nullium {
    const val MODID = "nullium"
    val JSON = Json { prettyPrint = true }

    @JvmField
    val log: Logger = LoggerFactory.getLogger(Nullium::class.simpleName)

    fun commonInit() {
        log.info("Nulls your ium!")
        NulConfigManager.init()
        NulCommandRegistry.init()
        BigSalmon.init()
    }

    fun id(path: String) = Identifier(MODID, path)
}
