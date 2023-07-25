package com.dotori.v2.domain.stu_info.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.member.exception.MemberNotFoundException
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
    ): List<SearchStudentListResDto> {

        val members = memberRepository.search(searchRequestDto)

        return members.map {
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