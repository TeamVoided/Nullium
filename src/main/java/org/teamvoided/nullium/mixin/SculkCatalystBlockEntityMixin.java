package org.teamvoided.nullium.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.SculkCatalystBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.teamvoided.nullium.module.NulliumGameRules;

import static org.teamvoided.nullium.module.NulliumGameRules.SCULK_SPREAD;
import static org.teamvoided.nullium.module.NulliumGameRules.getRuleValue;

@Mixin(SculkCatalystBlockEntity.class)
public class SculkCatalystBlockEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private static void nullium$spreadGameRule(World world, BlockPos pos, BlockState state, SculkCatalystBlockEntity skulkCatalystBlockEntity, CallbackInfo ci) {
        if (getRuleValue(world, SCULK_SPREAD).get() == NulliumGameRules.SpreadType.NONE) {
            skulkCatalystBlockEntity.catalystListener.getSculkBehavior().updateCharges(world, pos, world.getRandom(), false);
            ci.cancel();
        }
    }
}
