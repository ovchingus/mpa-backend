package com.itmo.mpa.controller.config

import com.itmo.mpa.service.exception.AttributesNotSetException
import com.itmo.mpa.service.exception.NotFoundException
import com.itmo.mpa.service.impl.predicate.parser.exception.UnexpectedTokenException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.Exception

@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

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

    @ExceptionHandler(AttributesNotSetException::class, UnexpectedTokenException::class)
    fun handleBadRequestException(exception: Exception, request: WebRequest): ResponseEntity<Any> {
        logger.error(exception.toString())
        val status = HttpStatus.BAD_REQUEST
        return handleExceptionInternal(exception,
                Error(exception.message, status.value()),
                HttpHeaders(),
                status,
                request)
    }

    override fun handleHttpMessageNotReadable(
            ex: HttpMessageNotReadableException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest
    ): ResponseEntity<Any> {
        return handleExceptionInternal(
                ex,
                Error(ex.message, status.value()),
                headers,
                status,
                request
        )
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
