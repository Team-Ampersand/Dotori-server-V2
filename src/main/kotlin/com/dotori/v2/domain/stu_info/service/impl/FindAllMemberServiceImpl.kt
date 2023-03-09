package com.dotori.v2.domain.stu_info.service.impl

import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.stu_info.presentation.data.res.FindAllStudentResDto
import com.dotori.v2.domain.stu_info.service.FindAllMemberService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class FindAllMemberServiceImpl(
    private val memberRepository: MemberRepository
) : FindAllMemberService {
    override fun execute(): List<FindAllStudentResDto> {
        val map = memberRepository.findAll(Sort.by(Sort.Direction.ASC, "stuNum"))
            .map {
                FindAllStudentResDto(
                    id = it.id,
                    gender = it.gender,
                    memberName = it.memberName,
                    stuNum = it.stuNum,
                    role = it.roles[0],
                    selfStudyCheck = it.selfStudyCheck
                )
            }
        return map
    }
}