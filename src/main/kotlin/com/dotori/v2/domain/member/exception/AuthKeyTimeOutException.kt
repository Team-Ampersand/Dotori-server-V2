package com.dotori.v2.domain.member.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class AuthKeyTimeOutException : BasicException(ErrorCode.MEMBER_OVER_CERTIFICATE_TIME) {
}