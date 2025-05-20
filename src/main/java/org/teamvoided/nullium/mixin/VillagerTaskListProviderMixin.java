package org.teamvoided.nullium.mixin;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.task.TaskControl;
import net.minecraft.entity.ai.brain.task.VillagerTaskListProvider;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

import static org.teamvoided.nullium.util.MixinLogicKt.crateCustomTasks;

@Mixin(VillagerTaskListProvider.class)
public class VillagerTaskListProviderMixin {

    @ModifyReturnValue(method = "createMeetTasks", at = @At("RETURN"))
    private static ImmutableList<Pair<Integer, ? extends TaskControl<? super VillagerEntity>>> addCustomTasks(ImmutableList<Pair<Integer, ? extends TaskControl<? super VillagerEntity>>> original, VillagerProfession profession, float speed) {
        List<Pair<Integer, ? extends TaskControl<? super VillagerEntity>>> newList = new ArrayList<>(original);
        newList.add(crateCustomTasks(profession, speed));
        return newList.stream().collect(ImmutableList.toImmutableList());
    }
}
