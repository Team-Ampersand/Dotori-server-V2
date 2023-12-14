package com.dotori.v2.domain.auth.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class ServiceNotFoundException : BasicException(ErrorCode.SERVICE_NOT_FOUND) {
}