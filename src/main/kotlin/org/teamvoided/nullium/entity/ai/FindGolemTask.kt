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
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.registry.Registries.VILLAGER_PROFESSION
import net.minecraft.unmapped.C_fudcfuiw
import org.teamvoided.nullium.data.tags.NulProfessionTags.REPAIRS_GOLEM
import org.teamvoided.nullium.init.NulGameRules.GOLEM_REPAIR
import org.teamvoided.nullium.init.NulGameRules.getBoolRule
import java.util.function.Consumer
import java.util.function.Predicate

object FindGolemTask {
    fun <T : LivingEntity> run(
        maxDistance: Int,
        target: MemoryModuleType<T>,
        speed: Float,
        completionRange: Int,
    ): TaskControl<VillagerEntity> {
        val dist = maxDistance * maxDistance
        val predicate =
            Predicate { golem: LivingEntity -> golem is IronGolemEntity && golem.crack != C_fudcfuiw.C_mihhozwa.NONE }
        return TaskBuilder.task { instance ->
            instance.group<MemoryAccessor<OptionalBox.Mu, T>, MemoryAccessor<OptionalBox.Mu, LookTarget>, MemoryAccessor<Const.Mu<Unit>, WalkTarget>, MemoryAccessor<IdF.Mu, VisibleLivingEntitiesCache>>(
                instance.registeredMemory<T>(target),
                instance.registeredMemory(MemoryModuleType.LOOK_TARGET),
                instance.absentMemory(MemoryModuleType.WALK_TARGET),
                instance.presentMemory(MemoryModuleType.VISIBLE_MOBS)
            ).apply(instance) { memTarget, memeLookAt, memeWalk, memeVisibleCache ->
                EntityTrigger { world, villager, l: Long ->
                    if (!world.getBoolRule(GOLEM_REPAIR)) return@EntityTrigger false
                    if (villager.isBaby) return@EntityTrigger false
                    val profession = VILLAGER_PROFESSION.getId(villager.villagerData.profession)
                    if (!VILLAGER_PROFESSION.getHolder(profession).get().isIn(REPAIRS_GOLEM)) return@EntityTrigger false
                    val visibleEntitiesCache = instance.getValue(memeVisibleCache)
                    if (visibleEntitiesCache.anyMatch(predicate)) {
                        val optional = visibleEntitiesCache
                            .getFirst { it.squaredDistanceTo(villager) <= dist.toDouble() && predicate.test(it) }
                        optional.ifPresent(Consumer {
                            @Suppress("UNCHECKED_CAST")
                            memTarget.remember(it as T)
                            memeLookAt.remember(EntityLookTarget(it, true))
                            memeWalk.remember(WalkTarget(EntityLookTarget(it, false), speed, completionRange))
                        })
                        return@EntityTrigger true
                    }
                    false
                }
            }
        }
    }
}