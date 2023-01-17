package com.dotori.v2.domain.member.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class MemberNotFoundException : BasicException(ErrorCode.MEMBER_NOT_FOUND) {
}