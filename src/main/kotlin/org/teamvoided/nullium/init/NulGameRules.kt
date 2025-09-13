package org.teamvoided.nullium.init

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry
import net.minecraft.world.GameRules
import net.minecraft.world.World

object NulGameRules {
    fun init() = Unit

    @JvmField
    val SCULK_SPREAD = register("nullium.sculkSpread", GameRules.Category.MISC,
        GameRuleFactory.createEnumRule(SpreadType.ALL)
    )

    @JvmField
    val FRAMERS_TRAMPLE = register("nullium.framersTrampleFarmland", GameRules.Category.MOBS,
        GameRuleFactory.createBooleanRule(false)
    )

    @JvmField
    val FEATHER_FALLING_TRAMPLE =
        register("nullium.featherFallingTramplesFarmland", GameRules.Category.PLAYER,
            GameRuleFactory.createBooleanRule(false)
        )

    @JvmStatic
    val GOLEM_REPAIR = register("nullium.golemRepair", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true))


    fun <T : GameRules.AbstractGameRule<T>> World.getRule(key: GameRules.Key<T>): T = gameRules.get(key)

    @JvmStatic
    fun <T : GameRules.AbstractGameRule<T>> getRuleValue(world: World, key: GameRules.Key<T>): T = world.getRule(key)

    @JvmStatic
    fun World.getBoolRule(key: GameRules.Key<GameRules.BooleanGameRule>): Boolean = this.gameRules.get(key).value

    @Suppress("unused")
    enum class SpreadType { NONE, VINES, ALL; }

    // Wrapper to fix java types
    fun <T : GameRules.AbstractGameRule<T>> register(name: String, category: GameRules.Category, type: GameRules.Type<T>)
            : GameRules.Key<T> = GameRuleRegistry.register(name, category, type)
}