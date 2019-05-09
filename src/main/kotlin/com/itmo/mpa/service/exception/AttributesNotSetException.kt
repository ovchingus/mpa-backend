package com.itmo.mpa.service.exception

class AttributesNotSetException(attributes: Collection<String>) : RuntimeException("Attributes not set $attributes")