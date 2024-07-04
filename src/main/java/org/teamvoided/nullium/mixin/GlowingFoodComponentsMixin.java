package org.teamvoided.nullium.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FoodComponents.class)
public class GlowingFoodComponentsMixin {
	@SuppressWarnings("unused")
	@Shadow
	public static FoodComponent GLOW_BERRIES = new FoodComponent
			.Builder()
			.hunger(2)
			.saturation(0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 600, 0), 1.0f)
			.build();
}
