package com.dotori.v2.domain.self_study.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.self_study.domain.repository.SelfStudyRepository
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberResDto
import com.dotori.v2.domain.self_study.service.GetSelfStudyByStuNumAndNameService
import com.dotori.v2.domain.self_study.presentation.dto.req.SelfStudySearchReqDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class GetSelfStudyByStuNumAndNameServiceImpl(
    private val memberRepository: MemberRepository
) : GetSelfStudyByStuNumAndNameService {
    override fun execute(searchRequestDto: SelfStudySearchReqDto): SelfStudyMemberListResDto {
        val members = memberRepository.searchSelfStudyMember(searchRequestDto)

        return SelfStudyMemberListResDto(
            members.mapIndexed{index, it ->
                SelfStudyMemberResDto(index + 1L, it)
            }
        )
    }
}