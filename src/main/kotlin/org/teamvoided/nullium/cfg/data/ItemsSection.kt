package org.teamvoided.nullium.cfg.data

import me.fzzyhmstrs.fzzy_config.annotations.Action
import me.fzzyhmstrs.fzzy_config.annotations.Comment
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup
import me.fzzyhmstrs.fzzy_config.config.ConfigSection
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedNumber

class ItemsSection : ConfigSection() {
    @Comment("If enabled glow berries, when eaten will apply glowing")
    @RequiresAction(Action.RESTART)
    var glowBerriesGlowing = true

    @Suppress("unused")
    var repairing = ConfigGroup("repairing")

    @JvmField
    @Comment("If enabled, item repair costs can be customized with datapacks")
    @RequiresAction(Action.RESTART)
    var customRepairCosts = true

    @Comment("Default base cost of repairing, will be used if none is set for an item with datapacks")
    var defaultBaseCost = ValidatedInt(1, 1024, 1, ValidatedNumber.WidgetType.TEXTBOX)

    @ConfigGroup.Pop
    @Comment("Default enchantment multiplier of repair cost, will be used if none is set for an item with datapacks")
    var defaultEnchantmentMultiplier = ValidatedDouble(0.6, 100.0, 0.0, ValidatedNumber.WidgetType.TEXTBOX)
}