package org.teamvoided.nullium.config.module

import kotlinx.serialization.Serializable
import org.teamvoided.nullium.config.ConfigInstance

class MiscellaneousCfg : ConfigInstance<MiscellaneousCfg.Companion.MiscellaneousData> {
    override val name: String = "Miscellaneous"
    override var data = MiscellaneousData()
    override fun serializer() = MiscellaneousData.serializer()

    companion object {
        @Serializable
        data class MiscellaneousData(
            val x: Boolean
        ) {
            constructor() : this(true)
        }
    }
}
