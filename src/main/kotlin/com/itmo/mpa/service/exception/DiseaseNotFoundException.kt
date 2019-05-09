package com.itmo.mpa.service.exception

class DiseaseNotFoundException(id: Long) : NotFoundException("Disease $id not found")