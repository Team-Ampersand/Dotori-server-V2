package com.dotori.v2.domain.self_study.service.impl

import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.self_study.domain.repository.SelfStudyRepository
import com.dotori.v2.domain.self_study.service.UpdateSelfStudyStatusService
import com.dotori.v2.domain.self_study.util.FindSelfStudyCountUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(rollbackFor = [Exception::class])
class UpdateSelfStudyStatusServiceImpl(
    private val selfStudyRepository: SelfStudyRepository,
    private val memberRepository: MemberRepository,
    private val selfStudyCountUtil: FindSelfStudyCountUtil
) : UpdateSelfStudyStatusService{
    override fun execute(){
        selfStudyRepository.deleteAll()
        updateSelfStudyStatus()
        updateUnBanSelfStudy()
        updateSelfStudyCheck()
        selfStudyCountUtil.findSelfStudyCount().updateCount(0)
    }

    private fun updateSelfStudyCheck() {
        memberRepository.findAllBySelfStudyCheck(true)
            .forEach { it.updateSelfStudyCheck(false) }
    }

    private fun updateUnBanSelfStudy() {
        memberRepository.findAllBySelfStudyStatus(SelfStudyStatus.IMPOSSIBLE)
            .filter { it.selfStudyExpiredDate!!.toLocalDate() == LocalDate.now() }
            .forEach { it.updateSelfStudyStatus(SelfStudyStatus.CAN) }
    }

    private fun updateSelfStudyStatus() {
        memberRepository.findAll()
            .filter { it.selfStudyStatus == SelfStudyStatus.APPLIED || it.selfStudyStatus == SelfStudyStatus.CANT }
            .forEach { it.updateSelfStudyStatus(SelfStudyStatus.CAN) }
    }
}