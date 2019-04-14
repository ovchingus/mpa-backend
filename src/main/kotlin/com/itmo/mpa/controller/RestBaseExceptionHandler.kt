package com.itmo.mpa.controller

import com.itmo.mpa.service.exception.NotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestBaseExceptionHandler : ResponseEntityExceptionHandler() {
    data class Error(val message: String?, val status: Int)


    @ExceptionHandler(NotFoundException::class)
    fun handleException(exception: NotFoundException, request: WebRequest): ResponseEntity<Any> {
        logger.error(exception.toString())
        val status = HttpStatus.NOT_FOUND
        return handleExceptionInternal(exception,
                Error(exception.message, status.value()),
                HttpHeaders(),
                status,
                request)
    }

    /**
     * Handle validation errors ([javax.validation.Valid]). The original method suppressed exception message
     */
    override fun handleMethodArgumentNotValid(exception: MethodArgumentNotValidException,
                                              headers: HttpHeaders, status: HttpStatus,
                                              request: WebRequest): ResponseEntity<Any> {

        return handleExceptionInternal(exception,
                Error(exception.message, status.value()),
                HttpHeaders(),
                status,
                request)
    }
}