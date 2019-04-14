package com.itmo.mpa.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerLogger {

    private val logger = LoggerFactory.getLogger(javaClass)!!

    @InitBinder
    fun initLoggingBinder(binder: WebDataBinder, request: WebRequest) {
        logger.trace("""
                    Binder: ${binder.objectName}
                    Request description: ${request.getDescription(true)}
                    Parameters: ${request.parameterMap}
                    Context path: ${request.contextPath}
                    """)
    }


}