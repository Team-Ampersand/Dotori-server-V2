package com.dotori.v2.domain.self_study.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.self_study.domain.repository.SelfStudyRepository
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberResDto
import com.dotori.v2.domain.self_study.service.GetSelfStudyByStuNumAndNameService
import com.dotori.v2.domain.stu_info.presentation.data.req.SearchRequestDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class GetSelfStudyByStuNumAndNameServiceImpl(
    private val selfStudyRepository: SelfStudyRepository,
) : GetSelfStudyByStuNumAndNameService {
    override fun execute(searchRequestDto: SearchRequestDto): SelfStudyMemberListResDto {
        val memberList = selfStudyRepository.findAllOrderByCreatedDateAsc().map { it.member }
        return getMemberCondition(searchRequestDto, memberList)
    }

    private fun getMemberCondition(searchRequestDto: SearchRequestDto, memberList: List<Member>): SelfStudyMemberListResDto {
        var filterMember: List<Member> = memberList

        if(searchRequestDto.name != null)
            filterMember = filterMember.filter { it.memberName == searchRequestDto.name }
        if(searchRequestDto.grade != null)
            filterMember = filterMember.filter { it.stuNum.substring(0, 1) == searchRequestDto.grade}
        if(searchRequestDto.classNum != null)
            filterMember = filterMember.filter { it.stuNum.substring(1, 2) == searchRequestDto.classNum }
        if(searchRequestDto.gender != null)
            filterMember = filterMember.filter { it.gender.name == searchRequestDto.gender }
        if(searchRequestDto.role != null)
            filterMember = filterMember.filter { it.roles[0].name == searchRequestDto.role }
        if(searchRequestDto.selfStudyCheck != null) {
            filterMember = filterMember.filter { it.selfStudyCheck == searchRequestDto.selfStudyCheck }
        }
        return SelfStudyMemberListResDto(filterMember.mapIndexed{index, it -> SelfStudyMemberResDto(index + 1L, it) })
    }
}