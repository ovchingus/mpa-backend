package com.itmo.mpa.service.mapping

import com.itmo.mpa.controller.request.StatusRequest
import com.itmo.mpa.controller.responce.StatusResponse
import com.itmo.mpa.model.Status

fun StatusRequest.toModel() = Status(status)

fun Status.toDto() = StatusResponse(status)

