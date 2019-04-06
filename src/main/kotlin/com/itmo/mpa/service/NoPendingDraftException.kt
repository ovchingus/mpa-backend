package com.itmo.mpa.service

class NoPendingDraftException(patientId: Long) : NotFoundException("No current draft for patient $patientId found")