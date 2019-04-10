package com.itmo.mpa.service.exception

class NoPendingDraftException(patientId: Long) : NotFoundException("No current draft for patient $patientId found")