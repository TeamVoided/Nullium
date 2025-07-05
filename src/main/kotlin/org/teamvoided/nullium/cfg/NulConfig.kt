package org.teamvoided.nullium.cfg

import me.fzzyhmstrs.fzzy_config.annotations.Action
import me.fzzyhmstrs.fzzy_config.annotations.Comment
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedMap
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedRegistryType
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedNumber.WidgetType
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import org.teamvoided.nullium.Nullium.MODID
import org.teamvoided.nullium.Nullium.id

@Suppress("unused")
class NulConfig : Config(id(MODID)) {

    // region VillagerChanges
    var villagerChanges = ConfigGroup("villager_changes", false)

    @JvmField
    var customVillagerFood = true

    @ConfigGroup.Pop
    @Comment("If enabled, will do a check for modded items that don't have registered a custom Villager Food")
    var moddedFoodFallback = true
    // endregion

    // region Composting
    var composting = ConfigGroup("composting", false)

    @Comment("Controls if composting changes are applied")
    @RequiresAction(Action.RELOAD_DATA)
    var compostingChanges = true

    @Comment("Adds or overrides a chance for a composter layer to spawn")
    @RequiresAction(Action.RELOAD_DATA)
    var compostEntries = ValidatedMap.Builder<Item, Float>()
        .keyHandler(ValidatedRegistryType.of(Registries.ITEM))
        .valueHandler(ValidatedFloat(.5f, 1f, 0.01f, WidgetType.SLIDER))
        .defaults(
            Items.GOLDEN_APPLE to 1f,
            Items.GOLDEN_CARROT to 1f
        ).build()

    @Comment("Removes an items chance to spawn a composter layer")
    @ConfigGroup.Pop
    @RequiresAction(Action.RELOAD_DATA)
    var entriesToRemove = mutableListOf<Item>()
    // endregion

    // region Item
    @Comment("If enabled glow berries, when eaten will apply glowing")
    var glowBerriesGlowing = true
    // endregion

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