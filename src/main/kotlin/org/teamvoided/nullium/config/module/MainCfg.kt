package org.teamvoided.nullium.config.module

import org.teamvoided.nullium.config.ConfigInstance
import org.teamvoided.nullium.config.data.MainData

class MainCfg : ConfigInstance<MainData> {
    override val name: String = "Main"
    override var data = MainData()
    override fun serializer() = MainData.serializer()
}
