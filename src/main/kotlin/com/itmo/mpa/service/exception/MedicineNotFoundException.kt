package com.itmo.mpa.service.exception

class MedicineNotFoundException(id: Long) : NotFoundException("Medicine $id not found")
