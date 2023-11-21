package com.dotori.v2.global.security.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class TokenExpiredException : BasicException(ErrorCode.TOKEN_EXPIRED) {
}