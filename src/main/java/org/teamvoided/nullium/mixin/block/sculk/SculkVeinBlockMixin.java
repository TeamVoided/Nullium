package org.teamvoided.nullium.mixin.block.sculk;

import net.minecraft.block.sculk.SculkBehavior;
import net.minecraft.block.sculk.SculkVeinBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.teamvoided.nullium.init.NulGameRules;

import java.util.Objects;

@Mixin(SculkVeinBlock.class)
public class SculkVeinBlockMixin {

    @Inject(method = "tryPlaceSculk", at = @At("HEAD"), cancellable = true)
    private void nullium$spreadGameRule(SculkBehavior sculkBehavior, WorldAccess world, BlockPos pos, RandomGenerator random, CallbackInfoReturnable<Boolean> cir) {
        var spreadType = Objects.requireNonNull(world.getServer()).getGameRules().get(NulGameRules.SCULK_SPREAD).get();
        if (spreadType != NulGameRules.SpreadType.ALL) {
            cir.setReturnValue(true);
        }
    }
}
