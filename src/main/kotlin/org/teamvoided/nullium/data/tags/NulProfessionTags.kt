package org.teamvoided.nullium.data.tags

import net.minecraft.registry.RegistryKeys
import org.teamvoided.nullium.Nullium.id
import org.teamvoided.nullium.util.tag

object NulProfessionTags {
    val REPAIRS_GOLEM = create("repairs_golem")


    private fun create(id: String) = RegistryKeys.VILLAGER_PROFESSION.tag(id(id))
}
