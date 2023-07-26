package com.dotori.v2.domain.stu_info.service.impl

import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.stu_info.presentation.data.res.FindAllStudentResDto
import com.dotori.v2.domain.stu_info.service.FindAllMemberService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class FindAllMemberServiceImpl(
    private val memberRepository: MemberRepository
) : FindAllMemberService {
    override fun execute(): List<FindAllStudentResDto> =
        memberRepository.findAll(Sort.by(Sort.Direction.ASC, "stuNum"))
            .map {
                FindAllStudentResDto(
                    id = it.id,
                    gender = it.gender,
                    memberName = it.memberName,
                    stuNum = it.stuNum,
                    role = it.roles[0],
                    selfStudyStatus = it.selfStudyStatus
                )
            }

}