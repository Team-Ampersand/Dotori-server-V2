package com.dotori.v2.global.error

class ErrorResponse(errorCode: ErrorCode){
    val msg: String
    val code: Int

    init {
        msg = errorCode.message
        code = errorCode.error
    }
}