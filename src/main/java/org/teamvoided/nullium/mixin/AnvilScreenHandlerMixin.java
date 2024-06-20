package org.teamvoided.nullium.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.teamvoided.nullium.module.Blacksmith;


@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin {

	@Shadow
	private int repairItemUsage;
	@Unique
	private static boolean isRepairing;

	@Inject(method = "onTakeOutput", at = @At("TAIL"))
	private void onTakeOutput(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
		isRepairing = false;
	}

	@ModifyExpressionValue(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(JJJ)J"))
	private long endFunny(long original, @Local(ordinal = 0) ItemStack originalItem, @Local(ordinal = 1) ItemStack copyItem, @Local(ordinal = 2) ItemStack modifierItem) {
		if (!modifierItem.isEmpty()) {
			if (copyItem.isDamageable() && copyItem.getItem().canRepair(originalItem, modifierItem)) {
				isRepairing = true;
				this.repairItemUsage = 1;
				copyItem.setDamage(0);
				return Blacksmith.calculateCost(copyItem);
			}
		}
		isRepairing = false;
		return original;
	}

	@Inject(method = "getNextCost", at = @At("RETURN"), cancellable = true)
	private static void removeCostsForRepair(int cost, CallbackInfoReturnable<Integer> cir) {
		if (isRepairing) cir.setReturnValue(cost);
	}
}
