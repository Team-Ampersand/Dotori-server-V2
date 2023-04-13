package com.dotori.v2.domain.stu_info.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.stu_info.presentation.data.req.SearchRequestDto
import com.dotori.v2.domain.stu_info.presentation.data.res.SearchStudentListResDto
import com.dotori.v2.domain.stu_info.service.SearchStudentService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class SearchStudentServiceImpl(
    private val memberRepository: MemberRepository
) : SearchStudentService {
    override fun execute(
        searchRequestDto: SearchRequestDto
    ): List<SearchStudentListResDto> =
        getMemberCondition(
            searchRequestDto,
            if (searchRequestDto.name != null)
                memberRepository.findAllByMemberNameStartingWithOrderByStuNumAsc(searchRequestDto.name)
            else
                memberRepository.findAllByOrderByStuNumAsc()
        )


    private fun getMemberCondition(
        searchRequestDto: SearchRequestDto,
        memberList: List<Member>
    ): List<SearchStudentListResDto> {

        val filterMember = searchRequestDto.run {
            memberList.asSequence().filter {
                if (grade != null) it.stuNum.startsWith(grade) else true
            }.filter {
                if (classNum != null) it.stuNum.substring(1, 2) == classNum else true
            }.filter {
                if (gender != null) it.gender.name == gender else true
            }.filter {
                if (role != null) it.roles.getOrNull(0)?.name == role else true
            }.filter {
                if(selfStudyStatus == SelfStudyStatus.IMPOSSIBLE) it.selfStudyStatus == selfStudyStatus || it.selfStudyStatus == SelfStudyStatus.CANT
                else if (selfStudyStatus != null) it.selfStudyStatus == selfStudyStatus else true
            }.toList()
        }

        return filterMember.map {
            SearchStudentListResDto(
                id = it.id,
                email = it.email,
                memberName = it.memberName,
                stuNum = it.stuNum,
                gender = it.gender,
                role = it.roles[0],
                selfStudyStatus = it.selfStudyStatus
            )
        }
    }
}