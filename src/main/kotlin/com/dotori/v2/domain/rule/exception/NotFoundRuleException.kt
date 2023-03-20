package com.dotori.v2.domain.rule.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class NotFoundRuleException : BasicException(ErrorCode.RULE_NOT_FOUND){
}