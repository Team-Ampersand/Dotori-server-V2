package com.dotori.v2.domain.selfstudy.service.impl

import com.dotori.v2.domain.selfstudy.domain.repository.SelfStudyRepository
import com.dotori.v2.domain.selfstudy.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.selfstudy.presentation.dto.res.SelfStudyMemberResDto
import com.dotori.v2.domain.selfstudy.service.GetSelfStudyRankService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class GetSelfStudyRankServiceImpl(
    private val selfStudyRepository: SelfStudyRepository,
) : GetSelfStudyRankService {
    override fun execute(): SelfStudyMemberListResDto =
        SelfStudyMemberListResDto(
            selfStudyRepository.findAllOrderByCreatedDateAsc()
                .mapIndexed { index, selfStudy -> SelfStudyMemberResDto(index+1L, selfStudy.member) }
        )
}