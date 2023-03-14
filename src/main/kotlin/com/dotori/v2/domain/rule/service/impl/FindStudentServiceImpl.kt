package com.dotori.v2.domain.rule.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.presentation.data.dto.MemberDto
import com.dotori.v2.domain.rule.presentation.data.res.MemberListResDto
import com.dotori.v2.domain.rule.service.FindStudentService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class FindStudentServiceImpl(
    private val memberRepository: MemberRepository,
) : FindStudentService {
    override fun execute(stuNum: String?, memberName: String?): MemberListResDto {
        return MemberListResDto(
            students =
            if (stuNum == null && memberName != null) {
                memberRepository.findAllByMemberName(memberName)
                    .map { it.toDto() }
            } else if (stuNum != null && memberName == null) {
                memberRepository.findAllByStuNum(stuNum)
                    .map { it.toDto() }
            } else {
                memberRepository.findAllByStuNumAndMemberName(stuNum = stuNum!!, memberName = memberName!!)
                    .map { it.toDto() }
            }
        )
    }

    private fun Member.toDto(): MemberDto =
        MemberDto(
            id = this.id,
            stuNum = this.stuNum,
            memberName = this.memberName,
            selfStudyStatus = this.selfStudyCheck,
            rule = this.ruleViolation
                .map { it.rule }
        )
}