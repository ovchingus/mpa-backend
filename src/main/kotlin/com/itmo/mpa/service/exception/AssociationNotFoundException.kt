package com.itmo.mpa.service.exception

class AssociationNotFoundException(id: Long) : NotFoundException("Association not found: $id")
