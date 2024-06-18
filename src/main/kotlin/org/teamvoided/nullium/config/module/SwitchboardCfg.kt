package org.teamvoided.nullium.config.module

import kotlinx.serialization.Serializable
import org.teamvoided.nullium.config.ConfigInstance

class SwitchboardCfg : ConfigInstance<SwitchboardCfg.Companion.SwitchboardData> {
    override val name: String = "Switchboard"
    override var data = SwitchboardData()
    override fun serializer() = SwitchboardData.serializer()

    companion object {
        @Serializable
        data class SwitchboardData(
            val enableMobScale: Boolean,
            @JvmField
            val enableHolderman: Boolean,
            @JvmField
            val enableBlacksmith: Boolean,
            val enableStackablePotions: Boolean,
        ) {

            constructor() : this(
                true,
                true,
                true,
                true
            )
        }
    }
}
