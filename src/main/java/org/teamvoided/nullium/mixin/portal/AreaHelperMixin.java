package org.teamvoided.nullium.mixin.portal;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.dimension.AreaHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.teamvoided.nullium.data.tags.NulliumBlockTags;

@Mixin(AreaHelper.class)
public class AreaHelperMixin {
    @Inject(method = "method_30487", at = @At("RETURN"), cancellable = true)
    private static void isInTagCheck(BlockState state, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(state.isIn(NulliumBlockTags.PORTAL_BLOCKS));
    }
}
