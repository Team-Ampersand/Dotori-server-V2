package com.dotori.v2.domain.member.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class TokenTypeNotValidException : BasicException(ErrorCode.TOKEN_REFRESH_FAIL)