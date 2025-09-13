package org.teamvoided.nullium.mixin.block.sculk;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.SculkCatalystBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.teamvoided.nullium.mixin.accessors.SculkCatalystBlockEntityAccessor;
import org.teamvoided.nullium.init.NulGameRules;

import static org.teamvoided.nullium.init.NulGameRules.SCULK_SPREAD;
import static org.teamvoided.nullium.init.NulGameRules.getRuleValue;

@Mixin(SculkCatalystBlockEntity.class)
public class SculkCatalystBlockEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private static void nullium$spreadGameRule(World world, BlockPos pos, BlockState state, SculkCatalystBlockEntity skulkCatalystBlockEntity, CallbackInfo ci) {
        if (getRuleValue(world, SCULK_SPREAD).get() == NulGameRules.SpreadType.NONE) {
            ((SculkCatalystBlockEntityAccessor) skulkCatalystBlockEntity).nullium_catalystListener().getSculkBehavior().updateCharges(world, pos, world.getRandom(), false);
            ci.cancel();
        }
    }
}
