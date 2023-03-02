package com.dotori.v2.domain.self_study.service.impl

import com.dotori.v2.domain.self_study.domain.repository.SelfStudyRepository
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberResDto
import com.dotori.v2.domain.self_study.service.GetSelfStudyByMemberNameService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class GetSelfStudyByMemberNameServiceImpl(
    private val selfStudyRepository: SelfStudyRepository
) : GetSelfStudyByMemberNameService{
    override fun execute(memberName: String): SelfStudyMemberListResDto =
        SelfStudyMemberListResDto(
            selfStudyRepository.findAllByMemberName(memberName)
                .map { SelfStudyMemberResDto(it) }
        )
}