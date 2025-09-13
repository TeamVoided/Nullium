package org.teamvoided.nullium.cfg

import me.fzzyhmstrs.fzzy_config.annotations.Action
import me.fzzyhmstrs.fzzy_config.annotations.Comment
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction
import me.fzzyhmstrs.fzzy_config.config.Config
import org.teamvoided.nullium.Nullium.MODID
import org.teamvoided.nullium.Nullium.id
import org.teamvoided.nullium.cfg.data.BlocksSection
import org.teamvoided.nullium.cfg.data.EntitiesSection
import org.teamvoided.nullium.cfg.data.ItemsSection

class NulConfig : Config(id(MODID)) {
    @JvmField
    var entities = EntitiesSection()

    @JvmField
    var blocks = BlocksSection()

    @JvmField
    var items = ItemsSection()

    @Comment("If enabled, Netherite Upgrade Smithing Template will be added to bartering")
    @RequiresAction(Action.RELOAD_DATA)
    var barterUpgrades = true
}