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

import java.util.Objects;

@Mixin(SculkCatalystBlockEntity.class)
public class SculkCatalystBlockEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private static void nullium$spreadGameRule(World world, BlockPos pos, BlockState state, SculkCatalystBlockEntity skulkCatalystBlockEntity, CallbackInfo ci) {
        var spreadType = Objects.requireNonNull(world.getServer()).getGameRules().get(NulliumGameRules.SCULK_SPREAD).get();
        if (spreadType == NulliumGameRules.SpreadType.NONE) {
            skulkCatalystBlockEntity.catalystListener.getSculkBehavior().updateCharges(world, pos, world.getRandom(), false);
            ci.cancel();
        }
    }
}
