package com.itmo.mpa.service.exception

class DoctorNotFoundException(query: Any) : NotFoundException("Doctor not found by $query")
