package org.teamvoided.nullium.data.gen.dyn

import net.minecraft.registry.BootstrapContext
import net.minecraft.registry.Holder
import net.minecraft.registry.RegistryKey
import net.minecraft.util.collection.DataPool
import net.minecraft.util.math.float_provider.ConstantFloatProvider
import net.minecraft.util.math.float_provider.FloatProvider
import net.minecraft.util.math.float_provider.UniformFloatProvider
import org.teamvoided.nullium.data.nullium.NulMobScalers
import org.teamvoided.nullium.data.nullium.data.MobScaler
import org.teamvoided.nullium.util.math.float_provider.WeightedListFloatProvider

object MobScalerCreator {
    fun bootstrap(c: BootstrapContext<MobScaler>) {
        c.make(NulMobScalers.SALMON, -0.2, 0.2)
        c.make(NulMobScalers.COD, -0.1, 0.1)
        c.make(NulMobScalers.TROPICAL_FISH, -0.1, 0.1)
        c.make(NulMobScalers.SQUID, (0 to 5), (-0.5 to 1))
        c.make(NulMobScalers.GLOW_SQUID, -0.3)
    }

    fun BootstrapContext<MobScaler>.make(
        key: RegistryKey<MobScaler>, vararg weightedEntry: Pair<Number, Int>,
    ): Holder.Reference<MobScaler> {
        val dataPool = DataPool.builder<FloatProvider>()
        for ((num, weight) in weightedEntry) {
            dataPool.addWeighted(ConstantFloatProvider.create(num.toFloat()), weight)
        }
        return create(key, WeightedListFloatProvider(dataPool.build()))
    }

    fun BootstrapContext<MobScaler>.make(key: RegistryKey<MobScaler>, min: Number, max: Number) =
        create(key, UniformFloatProvider.create(min.toFloat(), max.toFloat()))

    fun BootstrapContext<MobScaler>.make(key: RegistryKey<MobScaler>, const: Double) =
        create(key, ConstantFloatProvider.create(const.toFloat()))

    fun BootstrapContext<MobScaler>.create(key: RegistryKey<MobScaler>, prov: FloatProvider)
            : Holder.Reference<MobScaler> = register(key, MobScaler(prov))
}