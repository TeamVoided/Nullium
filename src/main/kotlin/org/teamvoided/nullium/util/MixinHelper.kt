@file:Suppress("CAST_NEVER_SUCCEEDS")

package org.teamvoided.nullium.util

import net.minecraft.item.ArmorMaterial
import net.minecraft.item.Item
import net.minecraft.item.ToolMaterials
import net.minecraft.recipe.Ingredient
import net.minecraft.registry.Holder
import net.minecraft.registry.tag.TagKey
import org.teamvoided.nullium.mixin.accessors.ArmorMaterialsAccessor
import org.teamvoided.nullium.mixin.accessors.ToolMaterialsAccessor
import java.util.function.Supplier

//Blacksmith Helpers

// ToolMaterials
fun ToolMaterials.set(tag: TagKey<Item>) = this.set { Ingredient.ofTag(tag) }
fun ToolMaterials.set(repairIngredient: Supplier<Ingredient>) =
    (this as ToolMaterialsAccessor).nullium_setRepairIngredient(repairIngredient)

// ArmorMaterials
fun Holder<ArmorMaterial>.set(tag: TagKey<Item>) = this.value().set { Ingredient.ofTag(tag) }
fun ArmorMaterial.set(tag: TagKey<Item>) = this.set { Ingredient.ofTag(tag) }
fun ArmorMaterial.set(repairIngredient: Supplier<Ingredient>) =
    (this as ArmorMaterialsAccessor).nullium_setRepairIngredient(repairIngredient)
