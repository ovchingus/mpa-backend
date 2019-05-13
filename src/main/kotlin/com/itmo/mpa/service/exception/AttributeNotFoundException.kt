package com.itmo.mpa.service.exception

class AttributeNotFoundException : NotFoundException {

    constructor(ids: Collection<Long>) : super("Attributes $ids not found")

    constructor(id: Long) : super("Attribute $id not found")
}