package com.dotori.v2.global.error.handler

import com.dotori.v2.global.error.DataErrorResponse
import com.dotori.v2.global.error.ErrorResponse
import com.dotori.v2.global.error.NoHandlerErrorResponse
import com.dotori.v2.global.error.ValidationErrorResponse
import com.dotori.v2.global.error.exception.BasicException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.servlet.NoHandlerFoundException

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this.javaClass.simpleName)

    @ExceptionHandler(BasicException::class)
    fun basicExceptionHandler(request: HttpServletRequest, ex: BasicException): ResponseEntity<ErrorResponse> {
        log.error(request.requestURI)
        log.error(ex.errorCode.message)
        val errorResponse = ErrorResponse(ex.errorCode)
        return ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(ex.errorCode.error))
    }

    @ExceptionHandler(BindException::class)
    fun handleBindException(e: BindException): ResponseEntity<ValidationErrorResponse> =
        ResponseEntity(ErrorResponse.of(e), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ValidationErrorResponse> =
        ResponseEntity(ErrorResponse.of(e), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(e: DataIntegrityViolationException): ResponseEntity<DataErrorResponse> =
        ResponseEntity(ErrorResponse.of(e), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(e: NoHandlerFoundException): ResponseEntity<NoHandlerErrorResponse> =
        ResponseEntity(ErrorResponse.of(e), HttpStatus.FORBIDDEN)

}
