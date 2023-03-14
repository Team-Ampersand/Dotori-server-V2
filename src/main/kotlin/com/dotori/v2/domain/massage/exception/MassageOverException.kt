package com.dotori.v2.domain.massage.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class MassageOverException : BasicException(ErrorCode.MASSAGE_OVER)