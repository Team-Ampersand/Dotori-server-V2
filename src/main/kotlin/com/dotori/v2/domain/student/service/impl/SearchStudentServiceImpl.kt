package com.dotori.v2.domain.student.service.impl

import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.student.presentation.data.req.SearchRequestDto
import com.dotori.v2.domain.student.presentation.data.res.SearchStudentListResDto
import com.dotori.v2.domain.student.service.SearchStudentService
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