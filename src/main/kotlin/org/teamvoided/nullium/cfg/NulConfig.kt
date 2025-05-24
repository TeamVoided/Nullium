package org.teamvoided.nullium.cfg

import me.fzzyhmstrs.fzzy_config.annotations.Action
import me.fzzyhmstrs.fzzy_config.annotations.Comment
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup
import org.teamvoided.nullium.Nullium.MODID
import org.teamvoided.nullium.Nullium.id

@Suppress("unused")
class NulConfig : Config(id(MODID)) {
    var villagerChanges = ConfigGroup("villager_changes", false)

    @JvmField
    var customVillagerFood = true

    @ConfigGroup.Pop
    @Comment("If enabled, will do a check for modded items that don't have registered a custom Villager Food")
    var moddedFoodFallback = true


    @JvmField
    @Comment("If enabled moss will be able to spread inder blocks in the #nullium:moss_grows_under tag")
    var betterMoss = true

    @Comment("If enabled full cakes will drop them self's")
    @RequiresAction(Action.RELOAD_DATA)
    var cakeDrops = true

    @Comment("If enabled, Netherite Upgrade Smithing Template will be added to bartering")
    @RequiresAction(Action.RELOAD_DATA)
    var barterUpgrades = true

}