package com.dotori.v2.domain.self_study.util

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.self_study.exception.AlreadyApplySelfStudyException
import com.dotori.v2.domain.self_study.exception.NotAppliedException
import org.springframework.stereotype.Component

@Component
class SelfStudyCheckUtil {
    fun isSelfStudyStatusCan(member: Member){
        if (member.selfStudyStatus != SelfStudyStatus.CAN)
            throw AlreadyApplySelfStudyException()
    }

    fun isSelfStudyStatusApplied(member: Member){
        if (member.selfStudyStatus != SelfStudyStatus.APPLIED)
            throw NotAppliedException()
    }
}