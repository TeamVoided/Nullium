package org.teamvoided.nullium.serlializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.minecraft.util.Identifier


class IdentifierSerializer : KSerializer<Identifier> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("id", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: Identifier) = encoder.encodeString(value.toString())
    override fun deserialize(decoder: Decoder): Identifier = Identifier.method_60654(decoder.decodeString())
}
