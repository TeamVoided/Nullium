package org.teamvoided.nullium.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.teamvoided.nullium.module.HolderMan;

@SuppressWarnings({"unused", "UnusedMixin"})
@Mixin(EndermanEntity.class)
abstract class EndermanEntityMixin extends MobEntity {

	@Shadow
	@Nullable
	public abstract BlockState getCarriedBlock();

	protected EndermanEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
		entityData = super.initialize(world, difficulty, spawnReason, entityData);
		if (this.getCarriedBlock() == null && !SpawnReason.isSpawner(spawnReason)) {
			HolderMan.getBlocks(world.toServerWorld(), (EndermanEntity) (Object) this);
		}
		return entityData;

	}


}
