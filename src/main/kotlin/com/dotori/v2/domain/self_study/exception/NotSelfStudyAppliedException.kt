package com.dotori.v2.domain.self_study.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class NotSelfStudyAppliedException : BasicException(ErrorCode.SELF_STUDY_NOT_APPLIED)