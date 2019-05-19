package com.itmo.mpa.service.exception

class AttributeNotFoundException(ids: Collection<Long>) : NotFoundException("Attributes $ids not found")
