package org.teamvoided.nullium.config.module

import org.teamvoided.nullium.config.ConfigInstance
import org.teamvoided.nullium.config.NulConfigManager
import org.teamvoided.nullium.config.data.MobScaleData

import java.io.File

class MobScaleCfg : ConfigInstance<MobScaleData> {
    override val name: String = "MobScale"
    override val configFile: File = NulConfigManager.getSubCfgFile("mob_scale.json")

    override var data = MobScaleData()
    override fun serializer() = MobScaleData.serializer()
}
