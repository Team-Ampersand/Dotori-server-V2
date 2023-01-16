package com.dotori.v2.domain.member.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class AuthKeyNotFoundException : BasicException(ErrorCode.MEMBER_EMAIL_HAS_NOT_AUTH_KEY) {
}