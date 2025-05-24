package org.teamvoided.nullium.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.Block;
import net.minecraft.block.WallMountedBlock;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.teamvoided.nullium.data.tags.NulliumBlockTags;

@Mixin(WallMountedBlock.class)
abstract class WallMountedBlockMixin {
    @ModifyReturnValue(method = "canPlaceAt(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z", at = @At("RETURN"))
    private static boolean tagPlacementCheck(boolean original, WorldView world, BlockPos pos, Direction direction) {
        if (original) return true;
        TagKey<Block> tag = switch (direction) {
            case DOWN -> NulliumBlockTags.SUPPORTS_SMALL_TOP;
            case UP -> NulliumBlockTags.SUPPORTS_SMALL_BOTTOM;
            default -> NulliumBlockTags.SUPPORTS_SMALL_SIDES;
        };
        return world.getBlockState(pos.offset(direction)).isIn(tag);
    }
}
