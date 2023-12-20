package com.dotori.v2.domain.selfstudy.service.impl

import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.selfstudy.domain.repository.SelfStudyRepository
import com.dotori.v2.domain.selfstudy.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.selfstudy.presentation.dto.res.SelfStudyMemberResDto
import com.dotori.v2.domain.selfstudy.service.GetSelfStudyByStuNumAndNameService
import com.dotori.v2.domain.selfstudy.presentation.dto.req.SelfStudySearchReqDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class GetSelfStudyByStuNumAndNameServiceImpl(
    private val memberRepository: MemberRepository,
    private val selfStudyRepository: SelfStudyRepository
) : GetSelfStudyByStuNumAndNameService {
    override fun execute(searchRequestDto: SelfStudySearchReqDto): SelfStudyMemberListResDto {
        val memberList = memberRepository.searchSelfStudyMember(searchRequestDto)
        val selfStudyList = selfStudyRepository.findByMemberIn(memberList)

        return SelfStudyMemberListResDto(
            selfStudyList.mapIndexed{index, it ->
                SelfStudyMemberResDto(index + 1L, it.member)
            }
        )
    }

}