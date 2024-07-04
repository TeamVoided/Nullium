package org.teamvoided.nullium.mixin;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Supplier;

@Mixin(ArmorMaterial.class)
public interface ArmorMaterialsAccessor {
    @Mutable
    @Accessor
    void setRepairIngredient(Supplier<Ingredient> repairIngredient);
}
