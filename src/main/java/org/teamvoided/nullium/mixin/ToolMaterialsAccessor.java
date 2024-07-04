package org.teamvoided.nullium.mixin;

import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Supplier;

@Mixin(ToolMaterials.class)
public interface ToolMaterialsAccessor {
    @Mutable
    @Accessor
    void setRepairIngredient(Supplier<Ingredient> repairIngredient);
}
