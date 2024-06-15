package org.teamvoided.nullium.config.module

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.MapSerializer
import net.minecraft.entity.EntityType
import net.minecraft.util.Identifier
import org.teamvoided.nullium.Nullium.JSON
import org.teamvoided.nullium.Nullium.log
import org.teamvoided.nullium.config.ConfigInstance
import org.teamvoided.nullium.config.NulConfigManager
import org.teamvoided.nullium.serlializers.IdentifierSerializer
import java.io.File
import kotlin.random.Random

class BigSalmonCfg : ConfigInstance<Map<EntityType<*>, BigSalmonCfg.ScaleProperties>> {
    override val configFile: File = NulConfigManager.getSubCfgFile("big_salmon.json")

    private var data = defaultData()

    override fun data(): Map<EntityType<*>, ScaleProperties> = data.toMap()


    override fun load(): Boolean {
        return if (!configFile.exists()) {
            log.warn("BigSalmon config file not found, creating default!")
            data = defaultData()
            save()
            false
        } else {
            val imported: Map<Identifier, ScaleProperties>
            try {
                imported = configFile.readText().let {
                    JSON.decodeFromString(
                        MapSerializer(IdentifierSerializer(), ScaleProperties.serializer()),
                        it
                    )
                }
            } catch (e: IllegalArgumentException) {
                log.error("Failed to load BigSalmon config file", e)
                return false
            }
            data = imported.mapNotNull { (id, props) ->
                val type = EntityType.get(id.toString())
                if (type.isEmpty) {
                    log.warn("Could not find EntityType for $id! Skipping...")
                    null
                } else type.get() to props
            }.toMap()
            log.info("Loaded BigSalmon config file")
            true
        }
    }

    override fun save(): Boolean {
        try {
            configFile.writeText(JSON.encodeToString(
                MapSerializer(IdentifierSerializer(), ScaleProperties.serializer()),
                data.mapKeys { EntityType.getId(it.key) }
            ))
        } catch (e: IllegalArgumentException) {
            log.error("Failed to save BigSalmon config file", e)
            return false
        }
        return true
    }

    companion object {
        @Suppress("MagicNumber")
        fun defaultData(): Map<EntityType<*>, ScaleProperties> =
            mapOf(
                EntityType.SALMON to ScaleProperties(0.8, 1.2),
                EntityType.COD to ScaleProperties(0.9, 1.1),
                EntityType.TROPICAL_FISH to ScaleProperties(0.9, 1.1),
            )
    }

    @Serializable
    class ScaleProperties(private val min: Double, private val max: Double) {
        fun random() = Random.nextDouble(min, max)
    }
}

