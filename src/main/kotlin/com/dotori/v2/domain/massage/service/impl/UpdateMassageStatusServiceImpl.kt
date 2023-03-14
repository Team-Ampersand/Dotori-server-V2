package com.dotori.v2.domain.massage.service.impl

import com.dotori.v2.domain.massage.domain.repository.MassageRepository
import com.dotori.v2.domain.massage.service.UpdateMassageStatusService
import com.dotori.v2.domain.massage.util.FindMassageCountUtil
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.MassageStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class UpdateMassageStatusServiceImpl(
    private val memberRepository: MemberRepository,
    private val massageRepository: MassageRepository,
    private val massageCountUtil: FindMassageCountUtil
) : UpdateMassageStatusService {
    override fun execute() {
        updateSelfStudyStatus()
        massageRepository.deleteAll()
        massageCountUtil.findMassageCount().clearCount()
    }

    private fun updateSelfStudyStatus() {
        memberRepository.findAllByMassageStatusOrMassageStatus(MassageStatus.APPLIED, MassageStatus.CANT)
            .forEach { it.updateMassageStatus(MassageStatus.CAN)
                println("it = ${it}")}
    }
}