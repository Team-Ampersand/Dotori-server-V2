package com.dotori.v2.global.error.exception

import com.dotori.v2.global.error.ErrorCode

open class BasicException(val errorCode: ErrorCode) : RuntimeException() {
}