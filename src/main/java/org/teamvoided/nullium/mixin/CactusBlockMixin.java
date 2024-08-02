package org.teamvoided.nullium.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.CactusBlock;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.teamvoided.nullium.data.tags.NulliumBlockTags;


@Mixin(CactusBlock.class)
public class CactusBlockMixin {

    @Inject(method = "canPlaceAt", at = @At("RETURN"), cancellable = true)
    private void nullium$customPlacement(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (world.getBlockState(pos.down()).isIn(NulliumBlockTags.CACTUS_SUPPORT)
                && !world.getBlockState(pos.up()).isLiquid()) {
            for (Direction dir : Direction.Type.HORIZONTAL) {
                var offsetState = world.getBlockState(pos.offset(dir));
                if (offsetState.isSolid() || world.getFluidState(pos.offset(dir)).isIn(FluidTags.LAVA)) {
                    cir.setReturnValue(false);
                    return;
                }
            }
            cir.setReturnValue(true);
            return;
        }
        cir.setReturnValue(false);
    }
}
