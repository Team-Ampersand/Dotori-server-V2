package com.dotori.v2.domain.rule.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.presentation.data.dto.MemberDto
import com.dotori.v2.domain.rule.presentation.data.req.StudentListReqDto
import com.dotori.v2.domain.rule.presentation.data.res.MemberListResDto
import com.dotori.v2.domain.rule.service.FindStudentService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class FindStudentServiceImpl(
    private val memberRepository: MemberRepository,
) : FindStudentService {

    override fun execute(studentListReqDto: StudentListReqDto): MemberListResDto =
        getMemberCondition(
            studentListReqDto,
            if (studentListReqDto.name != null)
                memberRepository.findAllByMemberNameStartingWithOrderByStuNumAsc(studentListReqDto.name)
            else
                memberRepository.findAllByOrderByStuNumAsc()
        )

    private fun getMemberCondition(studentListReqDto: StudentListReqDto, memberList: List<Member>): MemberListResDto =
        MemberListResDto(
            students = studentListReqDto.run {
                memberList.asSequence().filter {
                    if (grade != null) it.stuNum.startsWith(grade) else true
                }.filter {
                    if (classNum != null) it.stuNum.substring(1, 2) == classNum else true
                }.filter {
                    if (gender != null) it.gender == gender else true
                }.toList().map { it.toDto() }
            }
        )


    private fun Member.toDto(): MemberDto =
        MemberDto(
            id = this.id,
            stuNum = this.stuNum,
            memberName = this.memberName,
            gender = this.gender,
            rule = this.ruleViolation
                .map { it.rule }
        )
}