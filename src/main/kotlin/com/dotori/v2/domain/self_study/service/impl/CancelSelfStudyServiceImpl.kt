package com.dotori.v2.domain.self_study.service.impl

import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.self_study.domain.repository.SelfStudyRepository
import com.dotori.v2.domain.self_study.service.CancelSelfStudyService
import com.dotori.v2.domain.self_study.util.FindSelfStudyCountUtil
import com.dotori.v2.domain.self_study.util.SelfStudyCheckUtil
import com.dotori.v2.domain.self_study.util.ValidDayOfWeekAndHourUtil
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class CancelSelfStudyServiceImpl(
    private val userUtil: UserUtil,
    private val validDayOfWeekAndHourUtil: ValidDayOfWeekAndHourUtil,
    private val findSelfStudyCountUtil: FindSelfStudyCountUtil,
    private val selfStudyRepository: SelfStudyRepository,
    private val selfStudyCheckUtil: SelfStudyCheckUtil
) : CancelSelfStudyService {
    override fun execute() {
//        validDayOfWeekAndHourUtil.validateCancel()
        val findSelfStudyCount = findSelfStudyCountUtil.findSelfStudyCount()
        val member = userUtil.fetchCurrentUser()
        selfStudyCheckUtil.isSelfStudyStatusApplied(member)
        member.updateSelfStudyStatus(SelfStudyStatus.CANT)
        selfStudyRepository.deleteByMember(member)
        findSelfStudyCount.removeCount()
    }
}