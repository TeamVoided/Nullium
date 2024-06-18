package org.teamvoided.nullium.config.module

import org.teamvoided.nullium.config.ConfigInstance
import org.teamvoided.nullium.config.data.BlacksmithData

class BlacksmithCfg : ConfigInstance<BlacksmithData> {
    override val name: String = "Blacksmith"
    override var data = BlacksmithData()
    override fun serializer() = BlacksmithData.serializer()
}
