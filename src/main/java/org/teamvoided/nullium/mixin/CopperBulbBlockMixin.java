package org.teamvoided.nullium.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WaxedCopperBulbBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WaxedCopperBulbBlock.class)
public abstract class CopperBulbBlockMixin extends Block {
    @Shadow
    public abstract void setState(BlockState state, ServerWorld world, BlockPos pos);

    public CopperBulbBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "neighborUpdate", at = @At("HEAD"), cancellable = true)
    void nullium$customNeighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify, CallbackInfo ci) {
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.scheduleBlockTick(pos, this, 1);
        }
        ci.cancel();
    }

    @Inject(method = "onBlockAdded", at = @At("HEAD"), cancellable = true)
    void nullium$customOnBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (oldState.getBlock() != state.getBlock() && world instanceof ServerWorld serverWorld) {
            serverWorld.scheduleBlockTick(pos, this, 1);
        }
        ci.cancel();
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random) {
        this.setState(state, world, pos);
    }
}
