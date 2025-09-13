package org.teamvoided.nullium.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.teamvoided.nullium.module.Blacksmith;

import static org.teamvoided.nullium.Nullium.CONFIG;


@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @Shadow
    private int repairItemUsage;
    @Unique
    private static boolean nullium$isRepairing;

    @Inject(method = "onTakeOutput", at = @At("TAIL"))
    private void onTakeOutput(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        nullium$isRepairing = false;
    }

    @ModifyExpressionValue(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(JJJ)J"))
    private long endFunny(long original,
                          @Local(ordinal = 0) ItemStack originalItem,
                          @Local(ordinal = 1) ItemStack copyItem,
                          @Local(ordinal = 2) ItemStack modifierItem) {
        if (!CONFIG.customRepairCosts) {
            nullium$isRepairing = false;
            return original;
        }
        if (!modifierItem.isEmpty()) {
            if (copyItem.isDamageable() && copyItem.getItem().canRepair(originalItem, modifierItem)) {
                nullium$isRepairing = true;
                this.repairItemUsage = 1;
                copyItem.setDamage(0);

                return Blacksmith.calculateCost(player.getWorld(), copyItem);
            }
        }
        nullium$isRepairing = false;
        return original;
    }

    @ModifyReturnValue(method = "getNextCost", at = @At("RETURN"))
    private static int removeCostsForRepair(int original, int cost) {
        return nullium$isRepairing ? cost : original;
    }
}
