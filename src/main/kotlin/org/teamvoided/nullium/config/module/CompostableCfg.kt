package org.teamvoided.nullium.config.module

import org.teamvoided.nullium.config.ConfigInstance
import org.teamvoided.nullium.config.data.CompostableData

class CompostableCfg : ConfigInstance<CompostableData> {
    override val name: String = "Compostable"
    override var data = CompostableData()
    override fun serializer() = CompostableData.serializer()
}
