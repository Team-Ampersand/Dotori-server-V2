package com.dotori.v2.domain.self_study.excetpion

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class NotApplyDayException : BasicException(ErrorCode.SELF_STUDY_CANT_REQUEST_DATE)