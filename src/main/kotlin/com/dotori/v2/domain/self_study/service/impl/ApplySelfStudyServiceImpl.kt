package com.dotori.v2.domain.self_study.service.impl

import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.self_study.domain.entity.SelfStudy
import com.dotori.v2.domain.self_study.domain.repository.SelfStudyRepository
import com.dotori.v2.domain.self_study.excetpion.SelfStudyOverException
import com.dotori.v2.domain.self_study.service.ApplySelfStudyService
import com.dotori.v2.domain.self_study.util.FindSelfStudyCountUtil
import com.dotori.v2.domain.self_study.util.SaveSelfStudyUtil
import com.dotori.v2.domain.self_study.util.SelfStudyCheckUtil
import com.dotori.v2.domain.self_study.util.ValidDayOfWeekAndHourUtil
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(rollbackFor = [Exception::class])
class ApplySelfStudyServiceImpl(
    private val userUtil: UserUtil,
    private val findSelfStudyCountUtil: FindSelfStudyCountUtil,
    private val selfStudyCheckUtil: SelfStudyCheckUtil,
    private val saveSelfStudyUtil: SaveSelfStudyUtil,
    private val validDayOfWeekAndHourUtil: ValidDayOfWeekAndHourUtil
) : ApplySelfStudyService{
    override fun execute() {
        validDayOfWeekAndHourUtil.validate()
        val member = userUtil.fetchCurrentUser()
        val selfStudyCount = findSelfStudyCountUtil.findSelfStudyCount()
        if (selfStudyCount.count >= 50)
            throw SelfStudyOverException()
        selfStudyCheckUtil.isSelfStudyStatusCan(member)
        member.updateSelfStudyStatus(SelfStudyStatus.APPLIED)
        selfStudyCount.addCount()
        saveSelfStudyUtil.save(member)
    }
}