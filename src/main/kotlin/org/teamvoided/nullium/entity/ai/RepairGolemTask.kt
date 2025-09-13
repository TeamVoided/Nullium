package org.teamvoided.nullium.entity.ai

import com.google.common.collect.ImmutableMap
import net.minecraft.entity.ai.brain.MemoryModuleState
import net.minecraft.entity.ai.brain.MemoryModuleType
import net.minecraft.entity.ai.brain.task.Task
import net.minecraft.entity.passive.IronGolemEntity
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.registry.Registries.VILLAGER_PROFESSION
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvents
import net.minecraft.unmapped.C_fudcfuiw
import org.teamvoided.nullium.data.tags.NulProfessionTags.REPAIRS_GOLEM
import org.teamvoided.nullium.init.NulGameRules.GOLEM_REPAIR
import org.teamvoided.nullium.init.NulGameRules.getBoolRule
import kotlin.jvm.optionals.getOrNull

class RepairGolemTask : Task<VillagerEntity>(
    ImmutableMap.of(
        MemoryModuleType.INTERACTION_TARGET,
        MemoryModuleState.VALUE_PRESENT
    )
) {
    private var lastEndEntityAge: Long = 0


    override fun shouldRun(world: ServerWorld, villager: VillagerEntity): Boolean {
        if (!world.getBoolRule(GOLEM_REPAIR)) return false
        if (villager.isBaby) return false
        val profession = VILLAGER_PROFESSION.getId(villager.villagerData.profession)
        if (!VILLAGER_PROFESSION.getHolder(profession).get().isIn(REPAIRS_GOLEM)) return false
        if (villager.age % 10 != 0 || this.lastEndEntityAge != 0L && this.lastEndEntityAge + 100L > villager.age) return false

        return true
    }


    override fun run(world: ServerWorld, villager: VillagerEntity, l: Long) {
        val golem = villager.brain.getMemoryValue(MemoryModuleType.INTERACTION_TARGET)?.getOrNull() ?: return

        if (golem is IronGolemEntity && golem.crack != C_fudcfuiw.C_mihhozwa.NONE && villager.distanceTo(golem) <= 2f) {
            golem.heal(25.0f)
            golem.playSound(
                SoundEvents.ENTITY_IRON_GOLEM_REPAIR,
                1.0f,
                1.0f + (golem.random.nextFloat() - golem.random.nextFloat()) * 0.2f
            )
        }
    }

    override fun finishRunning(world: ServerWorld, villager: VillagerEntity, l: Long) {
        villager.getBrain().forget(MemoryModuleType.INTERACTION_TARGET)
        lastEndEntityAge = villager.age.toLong()
    }

    override fun shouldKeepRunning(world: ServerWorld, villager: VillagerEntity, l: Long): Boolean = false
}

