package com.itmo.mpa.service.exception

class NoCurrentStatusException(patientId: Long) : NotFoundException("No current status for patient $patientId found")