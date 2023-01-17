package com.dotori.v2.domain.email.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class EmailNotBeenException : BasicException(ErrorCode.MEMBER_EMAIL_HAS_NOT_BEEN_CERTIFICATE) {
}