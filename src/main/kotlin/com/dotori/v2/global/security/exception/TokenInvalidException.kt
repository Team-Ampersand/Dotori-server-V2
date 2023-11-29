package com.dotori.v2.global.security.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class TokenInvalidException : BasicException(ErrorCode.TOKEN_INVALID) {
}