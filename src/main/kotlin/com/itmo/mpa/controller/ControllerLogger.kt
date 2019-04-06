package com.itmo.mpa.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerLogger {

    val logger = LoggerFactory.getLogger(javaClass)!!

    @InitBinder
    fun initLoggingBinder(binder: WebDataBinder, request: WebRequest) {
        logger.info("Binder: ${binder.objectName}" +
                "Request description: ${request.getDescription(true)} " +
                "\n Parameters: ${request.parameterMap}" +
                "\n Context path: ${request.contextPath}")
    }
}