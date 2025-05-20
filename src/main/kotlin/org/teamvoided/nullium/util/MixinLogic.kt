package org.teamvoided.nullium.util

import com.google.common.collect.ImmutableMap
import com.mojang.datafixers.util.Pair
import net.minecraft.entity.ai.brain.MemoryModuleType
import net.minecraft.entity.ai.brain.task.CompositeTask
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.village.VillagerProfession
import org.teamvoided.nullium.entity.ai.FindGolemTask
import org.teamvoided.nullium.entity.ai.RepairGolemTask

fun crateCustomTasks(profession: VillagerProfession, speed: Float): Pair<Int, CompositeTask<VillagerEntity>> =
    Pair.of(
        6, CompositeTask<VillagerEntity>(
            ImmutableMap.of(),
            setOf(MemoryModuleType.INTERACTION_TARGET),
            CompositeTask.Order.ORDERED,
            CompositeTask.RunMode.TRY_ALL,
            listOf(
                Pair.of(FindGolemTask.run(8, MemoryModuleType.INTERACTION_TARGET, speed, 2), 6),
                Pair.of(RepairGolemTask(), 6)
            )
        )
    )
