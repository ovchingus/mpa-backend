package com.itmo.mpa.service.exception

class StateNotFoundException(id: Long) : NotFoundException("State $id not found")
