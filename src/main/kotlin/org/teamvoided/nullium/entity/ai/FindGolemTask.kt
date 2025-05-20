package org.teamvoided.nullium.entity.ai

import com.mojang.datafixers.kinds.Const
import com.mojang.datafixers.kinds.IdF
import com.mojang.datafixers.kinds.OptionalBox
import com.mojang.datafixers.util.Unit
import net.minecraft.entity.EntityTrigger
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.brain.*
import net.minecraft.entity.ai.brain.task.TaskBuilder
import net.minecraft.entity.ai.brain.task.TaskControl
import net.minecraft.entity.passive.IronGolemEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.unmapped.C_fudcfuiw
import org.teamvoided.nullium.module.NulliumGameRules.GOLEM_REPAIR
import org.teamvoided.nullium.module.NulliumGameRules.getBoolRule
import java.util.function.Consumer
import java.util.function.Predicate

object FindGolemTask {
    fun <T : LivingEntity> run(
        maxDistance: Int,
        target: MemoryModuleType<T>,
        speed: Float,
        completionRange: Int,
    ): TaskControl<LivingEntity> =
        create(maxDistance, { entity: LivingEntity -> true }, { entity: T -> true }, target, speed, completionRange)

    fun <E : LivingEntity, T : LivingEntity> create(
        maxDistance: Int,
        entityPredicate: Predicate<E>,
        targetPredicate: Predicate<T>,
        target: MemoryModuleType<T>,
        speed: Float,
        completionRange: Int,
    ): TaskControl<E> {
        val i = maxDistance * maxDistance
        val predicate = Predicate { entity: LivingEntity ->
            entity is IronGolemEntity && entity.crack != C_fudcfuiw.C_mihhozwa.NONE && targetPredicate.test(entity as T)
        }
        return TaskBuilder.task<E> { instance ->
            instance.group<MemoryAccessor<OptionalBox.Mu, T>, MemoryAccessor<OptionalBox.Mu, LookTarget>, MemoryAccessor<Const.Mu<Unit>, WalkTarget>, MemoryAccessor<IdF.Mu, VisibleLivingEntitiesCache>>(
                instance.registeredMemory<T>(target),
                instance.registeredMemory(MemoryModuleType.LOOK_TARGET),
                instance.absentMemory(MemoryModuleType.WALK_TARGET),
                instance.presentMemory(MemoryModuleType.VISIBLE_MOBS)
            )
                .apply<EntityTrigger<E>>(instance) { memTarget, memeLookAt, memeWalk, memeVisibleCache ->
                    EntityTrigger { world: ServerWorld, livingEntity3: E, l: Long ->
                        if (!world.getBoolRule(GOLEM_REPAIR)) return@EntityTrigger false
                        val visibleLivingEntitiesCache = instance.getValue(memeVisibleCache)
                        if (entityPredicate.test(livingEntity3) && visibleLivingEntitiesCache.anyMatch(predicate)) {
                            val optional =
                                visibleLivingEntitiesCache.getFirst { livingEntity2: LivingEntity ->
                                    livingEntity2.squaredDistanceTo(livingEntity3) <= i.toDouble()
                                            && predicate.test(livingEntity2)
                                }
                            optional.ifPresent(Consumer { livingEntity: LivingEntity ->
                                memTarget.remember(livingEntity as T)
                                memeLookAt.remember(EntityLookTarget(livingEntity, true))
                                memeWalk.remember(
                                    WalkTarget(EntityLookTarget(livingEntity, false), speed, completionRange)
                                )
                            })
                            return@EntityTrigger true
                        }
                        false
                    }
                }
        }
    }
}