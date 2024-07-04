package org.teamvoided.nullium.module

import org.teamvoided.nullium.config.NulConfigManager

object NulMiscellaneous {
    fun init(){
        val cfg = NulConfigManager.main.data()
    }
}