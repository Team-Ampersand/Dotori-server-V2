package com.dotori.v2.domain.selfstudy.service.impl

import com.dotori.v2.domain.selfstudy.domain.repository.SelfStudyRepository
import com.dotori.v2.domain.selfstudy.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.selfstudy.presentation.dto.res.SelfStudyMemberResDto
import com.dotori.v2.domain.selfstudy.service.GetSelfStudyByMemberNameService
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
                .mapIndexed { index, member -> SelfStudyMemberResDto(index+1L, member) }
        )
}