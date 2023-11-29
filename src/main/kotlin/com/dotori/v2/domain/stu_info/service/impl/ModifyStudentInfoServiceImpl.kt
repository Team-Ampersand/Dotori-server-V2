package com.dotori.v2.domain.stu_info.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import com.dotori.v2.domain.stu_info.presentation.data.req.ModifyStudentInfoRequest
import com.dotori.v2.domain.stu_info.service.ModifyStudentInfoService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(rollbackFor = [Exception::class])
class ModifyStudentInfoServiceImpl(
    private val memberRepository: MemberRepository
) : ModifyStudentInfoService {
    override fun execute(modifyStudentInfoRequest: ModifyStudentInfoRequest) {
        val member = memberRepository.findByIdOrNull(modifyStudentInfoRequest.memberId) ?: throw MemberNotFoundException()
        updateMemberInfo(member, modifyStudentInfoRequest)
    }

    private fun updateMemberInfo(member: Member, modifyStudentInfoRequest: ModifyStudentInfoRequest) {
        val newMember = Member(
            id = member.id,
            memberName = modifyStudentInfoRequest.memberName,
            stuNum = modifyStudentInfoRequest.stuNum,
            email = member.email,
            gender = modifyStudentInfoRequest.gender,
            roles = Collections.singletonList(modifyStudentInfoRequest.role),
            ruleViolation = member.ruleViolation,
            profileImage = member.profileImage
        )
        memberRepository.save(newMember)
    }
}