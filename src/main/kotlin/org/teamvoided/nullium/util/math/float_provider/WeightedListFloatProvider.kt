package org.teamvoided.nullium.util.math.float_provider

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.util.collection.DataPool
import net.minecraft.util.math.float_provider.FloatProvider
import net.minecraft.util.math.float_provider.FloatProviderType
import net.minecraft.util.random.RandomGenerator
import org.teamvoided.nullium.init.VoidLibNumberProviderTypes
import kotlin.math.max
import kotlin.math.min

class WeightedListFloatProvider(val distribution: DataPool<FloatProvider>) : FloatProvider() {
    private val min: Float
    private val max: Float

    init {
        val list = distribution.getEntries()
        var min = Float.MAX_VALUE
        var max = Float.MIN_VALUE

        for (present in list) {
            val k = present.data().min
            val l = present.data().max
            min = min(min, k)
            max = max(max, l)
        }

        this.min = min
        this.max = max
    }

    override fun get(random: RandomGenerator): Float {
        return this.distribution.getDataOrEmpty(random).orElseThrow(::IllegalStateException).get(random)
    }

    override fun getMin(): Float = min
    override fun getMax(): Float = max
    override fun getType(): FloatProviderType<*> = VoidLibNumberProviderTypes.WEIGHTED_FLOAT_LIST


    companion object {
        val CODEC: MapCodec<WeightedListFloatProvider> = RecordCodecBuilder.mapCodec {
            it.group(
                DataPool.createCodec(VALUE_CODEC).fieldOf("distribution")
                    .forGetter(WeightedListFloatProvider::distribution)
            ).apply(it, ::WeightedListFloatProvider)
        }
    }
}

