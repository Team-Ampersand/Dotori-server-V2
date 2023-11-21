package com.dotori.v2.global.config.dev

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Role
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

@Component
@Profile("dev")
class DevMemberConfig(
    private val memberRepository: MemberRepository
) {
    @PostConstruct
    fun generateMember(){
        val admin = Member(
            id = UUID.randomUUID(),
            memberName = "사감선생님",
            stuNum = "0000",
            email = "s00000@gsm.hs.kr",
            gender = "PENDING",
            roles = mutableListOf(Role.ROLE_ADMIN),
            ruleViolation = mutableListOf(),
            profileImage = null
        )
        memberRepository.save(admin)

        val developer = Member(
            id = UUID.randomUUID(),
            memberName = "도토리개발자",
            stuNum = "0001",
            email = "s00001@gsm.hs.kr",
            gender = "PENDING",
            roles = mutableListOf(Role.ROLE_DEVELOPER),
            ruleViolation = mutableListOf(),
            profileImage = null
        )
        memberRepository.save(developer)

        val councillor = Member(
            id = UUID.randomUUID(),
            memberName = "기숙사자치위원",
            stuNum = "0002",
            email = "s00002@gsm.hs.kr",
            gender = "PENDING",
            roles = mutableListOf(Role.ROLE_COUNCILLOR),
            ruleViolation = mutableListOf(),
            profileImage = null
        )
        memberRepository.save(councillor)

        val man = Member(
            id = UUID.randomUUID(),
            memberName = "남성유저",
            stuNum = "3101",
            email = "s00003@gsm.hs.kr",
            gender = "MALE",
            roles = mutableListOf(Role.ROLE_MEMBER),
            ruleViolation = mutableListOf(),
            profileImage = null
        )
        memberRepository.save(man)

        val woman = Member(
            id = UUID.randomUUID(),
            memberName = "여성유저",
            stuNum = "3201",
            email = "s00004@gsm.hs.kr",
            gender = "FEMALE",
            roles = mutableListOf(Role.ROLE_MEMBER),
            ruleViolation = mutableListOf(),
            profileImage = null
        )
        memberRepository.save(woman)
    }
}