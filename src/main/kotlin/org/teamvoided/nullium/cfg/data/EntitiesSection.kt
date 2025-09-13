package org.teamvoided.nullium.cfg.data

import me.fzzyhmstrs.fzzy_config.annotations.Comment
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup
import me.fzzyhmstrs.fzzy_config.config.ConfigSection

class EntitiesSection : ConfigSection() {
    // region VillagerChanges
    var villagerChanges = ConfigGroup("villager_changes")

    @JvmField
    @Comment("If enabled, villager food can be customized with datapacks")
    var customVillagerFood = true

    @ConfigGroup.Pop
    @Comment("If enabled, modded items are not with nullium to be custom foods. Will preform the vanilla food check")
    var moddedFoodFallback = true
    // endregion

    @JvmField
    @Comment("If enabled, Endermen can spawn holding a block")
    var endermanBlocksSpawn = true

    @Comment("If enabled, mob scale can be changed with datapacks")
    var customizableMobScale = true
}