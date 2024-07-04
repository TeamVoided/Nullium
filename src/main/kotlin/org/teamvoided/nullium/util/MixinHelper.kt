package org.teamvoided.nullium.util

import net.minecraft.item.Item
import net.minecraft.item.ToolMaterials
import net.minecraft.recipe.Ingredient
import net.minecraft.registry.tag.TagKey
import org.teamvoided.nullium.mixin.ToolMaterialsAccessor
import java.util.function.Supplier

fun ToolMaterials.set(tag: TagKey<Item>) = this.set { Ingredient.ofTag(tag) }

fun ToolMaterials.set(repairIngredient: Supplier<Ingredient>) =
    (this as ToolMaterialsAccessor).setRepairIngredient(repairIngredient)