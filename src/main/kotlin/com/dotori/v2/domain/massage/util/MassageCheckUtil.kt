package com.dotori.v2.domain.massage.util

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.MassageStatus
import com.dotori.v2.domain.selfstudy.exception.AlreadyApplySelfStudyException
import com.dotori.v2.domain.selfstudy.exception.NotAppliedException
import org.springframework.stereotype.Component

@Component
class MassageCheckUtil {
    fun isMassageStatusCan(member: Member){
        if (member.massageStatus != MassageStatus.CAN)
            throw AlreadyApplySelfStudyException()
    }

    fun isMassageStatusApplied(member: Member){
        if (member.massageStatus != MassageStatus.APPLIED)
            throw NotAppliedException()
    }
}