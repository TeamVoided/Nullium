package org.teamvoided.nullium.data.tags

import net.minecraft.registry.RegistryKeys
import org.teamvoided.nullium.Nullium.id
import org.teamvoided.nullium.util.tag

object NulliumMaterialTags {
    //Tool Materials
    val NETHERITE = material("netherite")
    val DIAMOND = material("diamond")
    val IRON = material("iron")
    val GOLD = material("gold")
    val STONE = material("stone")
    val WOOD = material("wood")

    //Armor Materials
    val CHAIN = material("chain")
    val LEATHER = material("leather")


    // Repair Tools
    val REPAIR_NETHERITE = repair("netherite")
    val REPAIR_DIAMOND = repair("diamond")
    val REPAIR_IRON = repair("iron")
    val REPAIR_GOLD = repair("gold")
    val REPAIR_STONE = repair("stone")
    val REPAIR_WOOD = repair("wood")

    // Repair Armor
    val REPAIR_CHAIN = repair("chain")
    val REPAIR_LEATHER = repair("leather")

    private fun material(id: String) = RegistryKeys.ITEM.tag(id("material/$id"))
    private fun repair(id: String) = RegistryKeys.ITEM.tag(id("material/repair/$id"))
}
