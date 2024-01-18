package com.dotori.v2.domain.selfstudy.service.impl

import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import com.dotori.v2.domain.selfstudy.domain.repository.SelfStudyRepository
import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyAppliedException
import com.dotori.v2.domain.selfstudy.presentation.dto.req.SelfStudyCheckReqDto
import com.dotori.v2.domain.selfstudy.service.UpdateSelfStudyCheckService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class UpdateSelfStudyCheckServiceImpl(
    private val selfStudyRepository: SelfStudyRepository,
    private val memberRepository: MemberRepository
) : UpdateSelfStudyCheckService {
    override fun execute(memberId: Long, selfStudyCheckReqDto: SelfStudyCheckReqDto) {
        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw MemberNotFoundException()

        if (!selfStudyRepository.existsByMember(member))
            throw NotSelfStudyAppliedException()

        member.updateSelfStudyCheck(selfStudyCheckReqDto.selfStudyCheck)
    }
}