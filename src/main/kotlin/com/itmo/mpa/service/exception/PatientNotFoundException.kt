package com.itmo.mpa.service.exception

class PatientNotFoundException(id: Long) : NotFoundException("Patient with id $id not found")
