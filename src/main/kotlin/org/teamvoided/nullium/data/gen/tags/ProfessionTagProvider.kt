package org.teamvoided.nullium.data.gen.tags

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.RegistryKeys
import net.minecraft.village.VillagerProfession
import org.teamvoided.nullium.data.tags.NulProfessionTags
import java.util.concurrent.CompletableFuture

class ProfessionTagProvider(output: FabricDataOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>) :
    FabricTagProvider<VillagerProfession>(output, RegistryKeys.VILLAGER_PROFESSION, registriesFuture) {
    override fun configure(arg: HolderLookup.Provider) {
        getOrCreateTagBuilder(NulProfessionTags.REPAIRS_GOLEM)
            .add(VillagerProfession.TOOLSMITH)
            .add(VillagerProfession.ARMORER)
            .add(VillagerProfession.WEAPONSMITH)
    }
}
