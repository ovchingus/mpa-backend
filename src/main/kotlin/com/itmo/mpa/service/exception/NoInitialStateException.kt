package com.itmo.mpa.service.exception

class NoInitialStateException(diseaseName: String)
    : NotFoundException("No initial state for disease $diseaseName found")
