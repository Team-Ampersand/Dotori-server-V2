package com.dotori.v2.domain.auth.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class SecretMismatchException: BasicException(ErrorCode.SECRET_MISMATCH) {
}