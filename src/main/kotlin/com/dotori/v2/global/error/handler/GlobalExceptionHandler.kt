package com.dotori.v2.global.error.handler

import com.dotori.v2.global.error.ErrorResponse
import com.dotori.v2.global.error.exception.BasicException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this.javaClass.simpleName)

    @ExceptionHandler(BasicException::class)
    fun basicExceptionHandler(request: HttpServletRequest, ex: BasicException): ResponseEntity<ErrorResponse> {
        log?.error(request.requestURI)
        log?.error(ex.message)
        val errorResponse = ErrorResponse(ex.errorCode)
        return ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(ex.errorCode.error))
    }
}