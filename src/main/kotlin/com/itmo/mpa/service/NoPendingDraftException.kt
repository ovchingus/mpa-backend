package com.itmo.mpa.service

class NoPendingDraftException(patientId: Long) : RuntimeException(patientId.toString())