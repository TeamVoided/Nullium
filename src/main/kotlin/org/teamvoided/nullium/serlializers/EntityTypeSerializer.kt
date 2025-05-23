package org.teamvoided.nullium.serlializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.minecraft.entity.EntityType
import kotlin.jvm.optionals.getOrNull


class EntityTypeSerializer : KSerializer<EntityType<*>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("type", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: EntityType<*>) =
        encoder.encodeString(EntityType.getId(value).toString())

    override fun deserialize(decoder: Decoder): EntityType<*> {
        val string = decoder.decodeString()
        return EntityType.get(string).getOrNull() ?: error("Failed to decode EntityType $string")
    }
}
