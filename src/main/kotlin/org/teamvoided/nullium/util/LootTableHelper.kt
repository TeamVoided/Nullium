package org.teamvoided.nullium.util

import net.minecraft.block.Block
import net.minecraft.item.ItemConvertible
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.BlockStatePropertyLootCondition
import net.minecraft.loot.condition.LootCondition
import net.minecraft.loot.condition.SurvivesExplosionLootCondition
import net.minecraft.loot.context.LootContextType
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.entry.LeafEntry
import net.minecraft.loot.entry.LootPoolEntry
import net.minecraft.loot.entry.LootTableEntry
import net.minecraft.loot.function.LootFunction
import net.minecraft.loot.provider.number.LootNumberProvider
import net.minecraft.predicate.StatePredicate
import net.minecraft.registry.RegistryKey
import net.minecraft.state.property.Property
import net.minecraft.util.Identifier
import net.minecraft.util.StringIdentifiable
import java.util.function.Consumer

inline fun lootTable(init: LootTableDSL.() -> Unit): LootTable.Builder {
    val dsl = LootTableDSL(LootTable.Builder())
    dsl.init()
    return dsl.get()
}

class LootTableDSL(private val builder: LootTable.Builder) {
    fun pool(pool: LootPool.Builder) = apply { builder.pool(pool) }
    fun pool(pool: LootPool) = apply { builder.pool(pool) }
    fun pools(pools: Collection<LootPool>) = apply { builder.pools(pools) }

    fun pool(fn: LootPoolDSL.() -> Unit) = apply { builder.pool(lootPool(fn)) }


    fun type(context: LootContextType) = apply { builder.type(context) }
    fun randomSequence(id: Identifier) = apply { builder.randomSequence(id) }

    fun applyFn(fnBuilder: LootFunction.Builder) = apply { builder.apply(fnBuilder) }

    fun conditionally(fnBuilder: LootCondition.Builder) = apply { builder.apply(fnBuilder as LootFunction.Builder) }
    fun applyFn(fn: LootFunction) = apply { builder.apply(fn) }
    fun applyFn(fns: Collection<LootFunction>) = apply { builder.apply(fns) }
    fun modifyPools(poolConsumer: Consumer<LootPool.Builder>) = apply { builder.modifyPools(poolConsumer) }

    fun build(): LootTable = builder.build()
    fun get() = builder
}


inline fun lootPool(init: LootPoolDSL.() -> Unit): LootPool {
    val dsl = LootPoolDSL(LootPool.Builder())
    dsl.init()
    return dsl.build()
}

class LootPoolDSL(private val builder: LootPool.Builder) {
    fun rolls(rolls: LootNumberProvider) = apply { builder.rolls(rolls) }
    fun bonusRolls(bonusRolls: LootNumberProvider) = apply { builder.bonusRolls(bonusRolls) }
    fun with(entry: LootPoolEntry.Builder<*>) = apply { builder.with(entry) }

    fun item(item: ItemConvertible, init: LeafEntryDSL.() -> Unit) = apply { builder.with(itemEntry(item, init)) }
    fun item(item: ItemConvertible, weight: Int) = apply { builder.with(itemEntry(item) { weight(weight) }) }

    fun lootTable(loot: LootTable, init: LeafEntryDSL.() -> Unit) = apply { builder.with(lootTableEntry(loot, init)) }
    fun lootTable(loot: RegistryKey<LootTable>, init: LeafEntryDSL.() -> Unit) =
        apply { builder.with(lootTableEntry(loot, init)) }

    fun conditionally(condition: LootCondition.Builder) = apply { builder.conditionally(condition) }
    fun build(): LootPool = builder.build()
    fun get() = builder
}


inline fun itemEntry(item: ItemConvertible, init: LeafEntryDSL.() -> Unit): LeafEntry.Builder<*> {
    val dsl = LeafEntryDSL(ItemEntry.builder(item))
    dsl.init()
    return dsl.get()
}

inline fun lootTableEntry(loot: LootTable, init: LeafEntryDSL.() -> Unit): LeafEntry.Builder<*> {
    val dsl = LeafEntryDSL(LootTableEntry.method_57631(loot))
    dsl.init()
    return dsl.get()
}

inline fun lootTableEntry(loot: RegistryKey<LootTable>, init: LeafEntryDSL.() -> Unit): LeafEntry.Builder<*> {
    val dsl = LeafEntryDSL(LootTableEntry.method_428(loot))
    dsl.init()
    return dsl.get()
}

class LeafEntryDSL(private val builder: LeafEntry.Builder<*>) {
    fun applyFn(fnBuilder: LootFunction.Builder) = apply { builder.apply(fnBuilder) }
    fun weight(weight: Int) = apply { builder.weight(weight) }
    fun quality(quality: Int) = apply { builder.quality(quality) }

    fun conditionally(condition: LootCondition.Builder) = apply { builder.conditionally(condition) }
    fun get() = builder
}

fun survivesExplosion(): LootCondition.Builder = SurvivesExplosionLootCondition.builder()

inline fun survivesExplosion(init: LootConditionDSL.() -> Unit): LootCondition.Builder {
    val dsl = LootConditionDSL(SurvivesExplosionLootCondition.builder())
    dsl.init()
    return dsl.get()
}

inline fun blockStateProperty(block: Block, init: StatePredicateBuilderDSL.() -> Unit): LootCondition.Builder {
    return BlockStatePropertyLootCondition.builder(block).properties(statePredicate(init))
}

class LootConditionDSL(private val builder: LootCondition.Builder) {
    fun and(condition: LootCondition.Builder) = apply { builder.and(condition) }
    fun or(condition: LootCondition.Builder) = apply { builder.or(condition) }
    fun get() = builder
}


inline fun statePredicate(init: StatePredicateBuilderDSL.() -> Unit): StatePredicate.Builder {
    val dsl = StatePredicateBuilderDSL(StatePredicate.Builder.create())
    dsl.init()
    return dsl.get()
}

class StatePredicateBuilderDSL(private val builder: StatePredicate.Builder) {
    fun exactMatch(property: Property<*>, valueName: String) = apply { builder.exactMatch(property, valueName) }
    fun exactMatch(property: Property<Int>, value: Int) = apply { builder.exactMatch(property, value) }
    fun exactMatch(property: Property<Boolean>, value: Boolean) = apply { builder.exactMatch(property, value) }
    fun <T> exactMatch(property: Property<T>, value: T) where T : Comparable<T>, T : StringIdentifiable =
        apply { builder.exactMatch(property, value) }
    fun get() = builder
}