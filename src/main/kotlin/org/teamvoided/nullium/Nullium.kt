package org.teamvoided.nullium

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.teamvoided.nullium.config.NulConfigManager
import org.teamvoided.nullium.init.NulCommandRegistry
import org.teamvoided.nullium.init.NulFabricEvents

@Suppress("unused")
object Nullium {
    const val MODID = "nullium"
    @OptIn(ExperimentalSerializationApi::class)
    val JSON = Json {
        prettyPrint = true
        prettyPrintIndent = "  "
    }

    @JvmField
    val log: Logger = LoggerFactory.getLogger(Nullium::class.simpleName)

    fun commonInit() {
        log.info("Nulls your ium!")
        NulConfigManager.init()
        NulCommandRegistry.init()
        NulFabricEvents.init()
    }

    fun id(path: String) = Identifier.of(MODID, path)
}
