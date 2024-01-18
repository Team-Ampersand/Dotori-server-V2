package com.dotori.v2.domain.selfstudy.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class NotSelfStudyCancelDayException : BasicException(ErrorCode.SELF_STUDY_CANT_CANCEL_DATE)