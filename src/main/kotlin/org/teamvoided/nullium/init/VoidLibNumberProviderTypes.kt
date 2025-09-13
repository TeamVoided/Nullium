package org.teamvoided.nullium.init

import com.mojang.serialization.MapCodec
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.math.float_provider.FloatProvider
import net.minecraft.util.math.float_provider.FloatProviderType
import org.teamvoided.nullium.Nullium.voidlib
import org.teamvoided.nullium.util.math.float_provider.WeightedListFloatProvider

object VoidLibNumberProviderTypes {

    val WEIGHTED_FLOAT_LIST: FloatProviderType<WeightedListFloatProvider> =
        registerFloat("weighted_list", WeightedListFloatProvider.CODEC)

    fun init() = Unit

    fun <P : FloatProvider> registerFloat(id: String, codec: MapCodec<P>): FloatProviderType<P> {
        return Registry.register(Registries.FLOAT_PROVIDER_TYPE, voidlib(id), FloatProviderType { codec })
    }
}