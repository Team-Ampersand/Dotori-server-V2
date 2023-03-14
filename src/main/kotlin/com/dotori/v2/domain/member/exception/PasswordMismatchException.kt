package com.dotori.v2.domain.member.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class PasswordMismatchException : BasicException(ErrorCode.MEMBER_PASSWORD_NOT_MATCHING) {
}