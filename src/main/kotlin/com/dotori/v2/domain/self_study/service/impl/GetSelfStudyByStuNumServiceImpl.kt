package com.dotori.v2.domain.self_study.service.impl

import com.dotori.v2.domain.self_study.domain.repository.SelfStudyRepository
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberResDto
import com.dotori.v2.domain.self_study.service.GetSelfStudyByStuNumService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class GetSelfStudyByStuNumServiceImpl(
    private val selfStudyRepository: SelfStudyRepository
) : GetSelfStudyByStuNumService {
    override fun execute(stuNum: String): SelfStudyMemberListResDto =
        SelfStudyMemberListResDto(
            selfStudyRepository.findAllByStuNum(stuNum)
                .mapIndexed { index, member -> SelfStudyMemberResDto(index+1L, member) }
        )
}