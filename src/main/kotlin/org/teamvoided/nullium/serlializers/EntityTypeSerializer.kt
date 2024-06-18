package org.teamvoided.nullium.serlializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.minecraft.entity.EntityType
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier


class EntityTypeSerializer : KSerializer<EntityType<*>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("type", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: EntityType<*>) = encoder.encodeString(value.toString())
    override fun deserialize(decoder: Decoder): EntityType<*> =
        Registries.ENTITY_TYPE.get(Identifier.parse(decoder.decodeString()))
}
