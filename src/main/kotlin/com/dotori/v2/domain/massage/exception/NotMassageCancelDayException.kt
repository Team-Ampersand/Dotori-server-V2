package com.dotori.v2.domain.massage.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class NotMassageCancelDayException : BasicException(ErrorCode.MASSAGE_CANT_CANCEL_THIS_TIME)