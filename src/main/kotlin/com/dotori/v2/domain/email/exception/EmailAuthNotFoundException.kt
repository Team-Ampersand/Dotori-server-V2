package com.dotori.v2.domain.email.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class EmailAuthNotFoundException : BasicException(ErrorCode.MAIL_AUTH_NOT_FOUND){
}