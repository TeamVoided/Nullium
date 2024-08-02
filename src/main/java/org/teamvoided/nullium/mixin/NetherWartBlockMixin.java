package org.teamvoided.nullium.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.teamvoided.nullium.data.tags.NulliumBlockTags;

@Mixin(NetherWartBlock.class)
public class NetherWartBlockMixin {
    @Inject(method = "canPlantOnTop", at = @At("RETURN"), cancellable = true)
    private void nullium$customPlacement(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(floor.isIn(NulliumBlockTags.NETHER_WART_SUPPORT));
    }
}
