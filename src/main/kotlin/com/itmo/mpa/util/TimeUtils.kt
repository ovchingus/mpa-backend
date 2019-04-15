package com.itmo.mpa.util

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.io.IOException
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class MyCustomSerializer : JsonSerializer<Instant>() {

    private val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC)

    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: Instant, gen: JsonGenerator, serializers: SerializerProvider) {
        val str = fmt.format(value)

        gen.writeString(str)
    }
}

class MyCustomDeserializer : JsonDeserializer<Instant>() {

    private val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC)

    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Instant {
        return Instant.from(fmt.parse(p.getText()))
    }
}