package com.dotori.v2.domain.selfstudy.service.impl

import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.selfstudy.exception.SelfStudyOverException
import com.dotori.v2.domain.selfstudy.service.ApplySelfStudyService
import com.dotori.v2.domain.selfstudy.util.FindSelfStudyCountUtil
import com.dotori.v2.domain.selfstudy.util.SaveSelfStudyUtil
import com.dotori.v2.domain.selfstudy.util.SelfStudyCheckUtil
import com.dotori.v2.domain.selfstudy.util.ValidDayOfWeekAndHourUtil
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class], isolation = Isolation.SERIALIZABLE)
class ApplySelfStudyServiceImpl(
    private val userUtil: UserUtil,
    private val findSelfStudyCountUtil: FindSelfStudyCountUtil,
    private val selfStudyCheckUtil: SelfStudyCheckUtil,
    private val saveSelfStudyUtil: SaveSelfStudyUtil,
    private val validDayOfWeekAndHourUtil: ValidDayOfWeekAndHourUtil
) : ApplySelfStudyService{
    override fun execute() {
        validDayOfWeekAndHourUtil.validateApply()

        val member = userUtil.fetchCurrentUser()

        val selfStudyCount = findSelfStudyCountUtil.findSelfStudyCount()

        if (selfStudyCount.count >= selfStudyCount.limit)
            throw SelfStudyOverException()

        selfStudyCheckUtil.isSelfStudyStatusCan(member)
        member.updateSelfStudyStatus(SelfStudyStatus.APPLIED)
        selfStudyCount.addCount()
        saveSelfStudyUtil.save(member)
    }
}