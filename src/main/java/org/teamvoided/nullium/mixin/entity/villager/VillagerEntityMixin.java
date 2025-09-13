package org.teamvoided.nullium.mixin.entity.villager;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.teamvoided.nullium.data.custom.VillagerFood;

import static org.teamvoided.nullium.Nullium.CONFIG;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity {
    public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @SuppressWarnings({"unchecked", "LocalMayBeArgsOnly"})
    @ModifyExpressionValue(method = "consumeAvailableFood", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
    <V> V customCanEatFood(V original, @Local ItemStack stack) {
        return CONFIG.entities.customVillagerFood ? (V) VillagerFood.canEatFood((Integer) original, stack, getWorld()) : original;
    }

    @ModifyReturnValue(method = "getAvailableFood", at = @At("RETURN"))
    int gatherCustomFoods(int original, @Local SimpleInventory inventory) {
        return CONFIG.entities.customVillagerFood ? VillagerFood.getFoodValues(inventory, getWorld()) : original;
    }

    @ModifyReturnValue(method = "canGather", at = @At("RETURN"))
    boolean canPickUpCustom(boolean original, ItemStack stack) {
        return CONFIG.entities.customVillagerFood ? VillagerFood.canPickUp(stack, getWorld(), original) : original;
    }
}
