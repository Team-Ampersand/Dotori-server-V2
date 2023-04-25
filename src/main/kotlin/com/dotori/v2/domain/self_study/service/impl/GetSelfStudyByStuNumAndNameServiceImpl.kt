package com.dotori.v2.domain.self_study.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
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
    private val selfStudyRepository: SelfStudyRepository,
) : GetSelfStudyByStuNumAndNameService {
    override fun execute(searchRequestDto: SelfStudySearchReqDto): SelfStudyMemberListResDto {
        val memberList = selfStudyRepository.findAllOrderByCreatedDateAsc()
            .filter {
                if(searchRequestDto.name != null) it.member.memberName == searchRequestDto.name else true
            }
            .map { it.member }
        return getMemberCondition(searchRequestDto, memberList)
    }

    private fun getMemberCondition(searchRequestDto: SelfStudySearchReqDto, memberList: List<Member>): SelfStudyMemberListResDto {
        val filterMember = searchRequestDto.run {
            memberList.filter {
                if (grade != null) it.stuNum.startsWith(grade) else true
            }.filter {
                if (classNum != null) it.stuNum.substring(1, 2) == classNum else true
            }.filter {
                if (gender != null) it.gender.name == gender else true
            }.toList()
        }

        return SelfStudyMemberListResDto(filterMember.mapIndexed{index, it -> SelfStudyMemberResDto(index + 1L, it) })
    }
}