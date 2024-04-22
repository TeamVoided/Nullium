package org.teamvoided.nullium

import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused")
object Nullium {
    const val MODID = "nullium"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(Nullium::class.simpleName)

    fun commonInit() { log.info("Nulls your ium .?.") }
    fun id(path: String) = Identifier(MODID, path)
}
