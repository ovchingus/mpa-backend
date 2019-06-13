package com.itmo.mpa.util

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class InstantSerializer : JsonSerializer<Instant>() {

    private val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC)

    override fun serialize(value: Instant, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeString(fmt.format(value))
    }
}

class InstantDeserializer : JsonDeserializer<Instant>() {

    private val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC)

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Instant {
        return Instant.from(fmt.parse(p.text))
    }
}
