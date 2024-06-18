package org.teamvoided.nullium.config.data

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import org.teamvoided.nullium.serlializers.EntityTypeSerializer
import kotlin.random.Random


typealias LivingType = EntityType<out LivingEntity>

@Serializable
data class MobScaleData(
    val scales: List<ScaleEntry>
) {
    constructor() : this(
        listOf(
            ScaleEntry(EntityType.SALMON, 0.8, 1.2),
            ScaleEntry(EntityType.COD, 0.9, 1.1),
            ScaleEntry(EntityType.TROPICAL_FISH, 0.9, 1.1),
            ScaleEntry(EntityType.SQUID, listOf(1.0, 1.0, 1.0, 1.0, 1.0, 0.5)),
            ScaleEntry(EntityType.GLOW_SQUID, 0.7)
        )
    )
}


@Serializable
class ScaleEntry(
    @Serializable(with = EntityTypeSerializer::class) val entity: EntityType<out @Contextual Entity>,
    val properties: ScaleProperties
) {
    constructor(type: LivingType, min: Double, max: Double) : this(type, RandomScaleProperty(min, max))
    constructor(type: LivingType, const: Double) : this(type, ConstantScaleProperty(const))
    constructor(type: LivingType, list: List<Double>) : this(type, ListScaleProperty(list))

    fun getScale() = properties.getScale()

}

@Serializable
sealed class ScaleProperties {
    abstract fun getScale(): Double
}

@Serializable
@SerialName("RANDOM")
class RandomScaleProperty(private val min: Double, private val max: Double) : ScaleProperties() {
    override fun getScale() = Random.nextDouble(min, max)
}

@Serializable
@SerialName("LIST")
class ListScaleProperty(private val list: List<Double>) : ScaleProperties() {
    override fun getScale() = list.random()
}

@Serializable
@SerialName("CONSTANT")
class ConstantScaleProperty(private val const: Double) : ScaleProperties() {
    override fun getScale() = const
}
