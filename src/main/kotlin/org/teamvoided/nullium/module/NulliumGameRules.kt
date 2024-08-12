package org.teamvoided.nullium.module

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory.createEnumRule
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry.register
import net.minecraft.world.GameRules
import net.minecraft.world.GameRules.Category
import net.minecraft.world.World

object NulliumGameRules {
    fun init() = Unit

    @JvmField
    val SCULK_SPREAD = register("nullium.sculkSpread", Category.MISC, createEnumRule(SpreadType.ALL))
//    @JvmField
//    val


    fun <T : GameRules.AbstractGameRule<T>> World.getRule(key: GameRules.Key<T>): T = gameRules.get(key)
    @JvmStatic
    fun <T : GameRules.AbstractGameRule<T>> getRuleValue(world: World, key: GameRules.Key<T>): T = world.getRule(key)

    enum class SpreadType { NONE, VINES, ALL; }
}