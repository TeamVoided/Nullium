package org.teamvoided.nullium.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallMountedBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.teamvoided.nullium.data.tags.NulliumBlockTags;

@Mixin(WallMountedBlock.class)
abstract class WallMountedBlockMixin {
	@Inject(method = "canPlaceAt(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z", at = @At("RETURN"), cancellable = true)
	private static void customPlacement(WorldView world, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue()) {
			if (direction == Direction.DOWN) {
				BlockState block = world.getBlockState(pos.down());
				cir.setReturnValue(block.isIn(NulliumBlockTags.SUPPORT_SMALL_TOP));
			} else if (direction == Direction.UP) {
				BlockState block = world.getBlockState(pos.up());
				cir.setReturnValue(block.isIn(NulliumBlockTags.SUPPORT_SMALL_BOTTOM));
			}
		}
	}
}
