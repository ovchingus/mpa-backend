package com.itmo.mpa.service.exception

class StatusNotFoundException(patientId: Long, statusId: Long)
    : NotFoundException("No status $statusId found for patient $patientId")
