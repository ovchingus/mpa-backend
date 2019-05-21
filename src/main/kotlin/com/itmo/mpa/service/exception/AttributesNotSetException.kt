package com.itmo.mpa.service.exception

class AttributesNotSetException(attributes: Collection<Long>) : RuntimeException("Attributes not set $attributes")