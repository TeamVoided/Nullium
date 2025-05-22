package org.teamvoided.nullium.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.BlockState;
import net.minecraft.block.MossBlock;
import net.minecraft.registry.Holder;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.teamvoided.nullium.data.world.gen.NulConfiguredFeatures;

import java.util.Optional;

import static org.teamvoided.nullium.config.FutureConfigKt.BETTER_MOSS;
import static org.teamvoided.nullium.data.tags.NulliumBlockTags.MOSS_GROWS_UNDER;

@Mixin(MossBlock.class)
public class MossBlockMixin {

    @ModifyReturnValue(method = "isFertilizable", at = @At("RETURN"))
    boolean makeFertilizable(boolean original, WorldView world, BlockPos pos, BlockState state) {
        return original || world.getBlockState(pos.up()).isIn(MOSS_GROWS_UNDER);
    }

    @SuppressWarnings({"unchecked", "OptionalUsedAsFieldOrParameterType", "rawtypes"})
    @ModifyExpressionValue(method = "method_46685", at = @At(value = "INVOKE", target = "Lnet/minecraft/registry/Registry;getHolder(Lnet/minecraft/registry/RegistryKey;)Ljava/util/Optional;"))
    private static <T> Optional<Holder.Reference<T>> theSecond(Optional<Holder.Reference<T>> original, Registry registry) {
        if (BETTER_MOSS) {
            return registry.getHolder(NulConfiguredFeatures.ENHANCED_MOSS_PATCH_BONEMEAL);
        }
        return original;
    }
}
