package org.teamvoided.nullium.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.WitherRoseBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.teamvoided.nullium.data.tags.NulliumBlockTags;

@Mixin(WitherRoseBlock.class)
abstract class WitherRoseBlockMixin {
	@Inject(method = "canPlantOnTop", at = @At("RETURN"), cancellable = true)
	void customPlacement(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(floor.isIn(NulliumBlockTags.WITHER_ROSE_PLACEABLE));
	}
}
