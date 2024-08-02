package org.teamvoided.nullium.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.RootsBlock;
import net.minecraft.block.SproutsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.teamvoided.nullium.data.tags.NulliumBlockTags;

@Mixin({RootsBlock.class, SproutsBlock.class})
public class RootsBlockMixin {
    @Inject(method = "canPlantOnTop", at = @At("RETURN"), cancellable = true)
    private void nullium$customPlacement(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(floor.isIn(NulliumBlockTags.NYLIUM_PLANTS_SUPPORT));
    }
}
