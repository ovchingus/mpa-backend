package com.itmo.mpa.service.exception

class AttributeNotFoundException(name: String) : NotFoundException("Attribute $name not found")