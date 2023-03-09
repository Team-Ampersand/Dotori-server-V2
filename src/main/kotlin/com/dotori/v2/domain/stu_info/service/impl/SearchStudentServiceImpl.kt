package com.dotori.v2.domain.stu_info.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.stu_info.presentation.data.req.SearchRequestDto
import com.dotori.v2.domain.stu_info.presentation.data.res.SearchStudentListResDto
import com.dotori.v2.domain.stu_info.service.SearchStudentService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class SearchStudentServiceImpl(
    private val memberRepository: MemberRepository
) : SearchStudentService {
    override fun execute(
        searchRequestDto: SearchRequestDto
    ): List<SearchStudentListResDto> {
        val memberList = memberRepository.findAll(Sort.by(Sort.Direction.ASC, "stuNum"))
        return getMemberCondition(searchRequestDto, memberList)
    }

    private fun getMemberCondition(searchRequestDto: SearchRequestDto, memberList: List<Member>): List<SearchStudentListResDto> {
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
        return filterMember.
                map { SearchStudentListResDto(
                    email = it.email,
                    memberName = it.memberName,
                    stuNum = it.stuNum,
                    gender = it.gender,
                    role = it.roles[0],
                    selfStudyCheck = it.selfStudyCheck
                ) }
    }
}