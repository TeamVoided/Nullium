package org.teamvoided.nullium

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.teamvoided.nullium.cfg.NulConfig
import org.teamvoided.nullium.config.NulConfigManager
import org.teamvoided.nullium.init.*
import org.teamvoided.nullium.module.Blacksmith
import org.teamvoided.nullium.module.Compostable
import org.teamvoided.nullium.module.NulliumGameRules

@Suppress("unused")
object Nullium {
    const val MODID = "nullium"

    @OptIn(ExperimentalSerializationApi::class)
    val JSON = Json {
        prettyPrint = true
        prettyPrintIndent = "  "
    }

    @JvmField
    var CONFIG = ConfigApi.registerAndLoadConfig(::NulConfig)

    @JvmField
    val log: Logger = LoggerFactory.getLogger(Nullium::class.simpleName)

    fun commonInit() {
        log.info("Nulls your ium!")
        NulConfigManager.init()
        // Reg init
        NulFeatures.init()
        NulRegistryKeys.init()
        NulCommandRegistry.init()
        NulliumGameRules.init()
        VoidLibNumberProviderTypes.init()
        // Events
        NulFabricEvents.init()
        // Mod init
        Compostable.init()
        Blacksmith.repairOverrides()
    }

    fun voidlib(path: String) = Identifier.of("voidlib", path)

    fun id(namespace: String, path: String) = Identifier.of(namespace, path)
    fun id(path: String) = Identifier.of(MODID, path)
    fun mc(path: String) = Identifier.ofDefault(path)
}
