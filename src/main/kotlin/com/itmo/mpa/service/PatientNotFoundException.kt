package com.itmo.mpa.service

class PatientNotFoundException(id: Long) : NotFoundException("Patient with id $id not found")