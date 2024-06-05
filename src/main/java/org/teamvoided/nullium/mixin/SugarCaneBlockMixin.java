package org.teamvoided.nullium.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.teamvoided.nullium.data.NulliumBlockTags;
import org.teamvoided.nullium.data.NulliumFluidTags;

@Mixin(SugarCaneBlock.class)
abstract class SugarCaneBlockMixin {

    @Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
    void customPlacement(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = world.getBlockState(pos.down());
        if (blockState.isOf((SugarCaneBlock) (Object) this)) {
            cir.setReturnValue(true);
            return;
        }
        if (blockState.isIn(NulliumBlockTags.CANE_SUPPORT)) {
            BlockPos blockPos = pos.down();
            for (Direction direction : Direction.Type.HORIZONTAL) {
                BlockPos off = blockPos.offset(direction);
                if (world.getBlockState(off).isIn(NulliumBlockTags.CANE_HYDRATION)
                        || world.getFluidState(off).isIn(NulliumFluidTags.CANE_HYDRATION)) {
                    cir.setReturnValue(true);
                    return;
                }
            }
        }
        cir.setReturnValue(false);
    }
}
