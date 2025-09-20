package org.teamvoided.nullium.data.nullium.data

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.util.math.float_provider.FloatProvider
import net.minecraft.util.random.RandomGenerator
import org.teamvoided.nullium.init.NulRegistryKeys.MOB_SCALER

data class MobScaler(val provider: FloatProvider) {
    operator fun get(random: RandomGenerator): Float = provider.get(random)

    companion object {
        fun Entity.getScaler(): MobScaler? = registryManager.get(MOB_SCALER).get(EntityType.getId(type))
        val CODEC: Codec<MobScaler> = RecordCodecBuilder.create<MobScaler> { instance ->
            instance.group(
                FloatProvider.createValidatedCodec(-64f, 64f).fieldOf("scale").forGetter(MobScaler::provider)
            ).apply(instance, ::MobScaler)
        }
    }
}