package com.dotori.v2.global.error

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.validation.BindingResult
import org.springframework.web.servlet.NoHandlerFoundException

class ErrorResponse(errorCode: ErrorCode){
    val msg: String
    val code: Int

    init {
        msg = errorCode.message
        code = errorCode.error
    }

    companion object {

        fun of(e: BindingResult): ValidationErrorResponse {
            val fieldErrorMap = e.fieldErrors.associateBy({ it.field }, { it.defaultMessage })

            return ValidationErrorResponse(
                fieldErrorMap,
                ErrorCode.BAD_REQUEST.error
            )
        }

        fun of(e: DataIntegrityViolationException) = DataErrorResponse(
            message = e.message.toString(),
            status = ErrorCode.BAD_REQUEST.error
        )

        fun of(e: NoHandlerFoundException) = NoHandlerErrorResponse(
            message = e.message.toString(),
            status = ErrorCode.BAD_REQUEST.error
        )
    }
}

data class ValidationErrorResponse(
    val fieldError: Map<String, String?>,
    val status: Int
)

data class DataErrorResponse(
    val message: String,
    val status: Int
)

data class NoHandlerErrorResponse(
    val message: String,
    val status: Int
)