package com.dotori.v2.global.config.dev

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.global.config.security.jwt.TokenProvider
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
@Profile("dev")
class DevMemberConfig(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    @PostConstruct
    fun generateMember(){
        val admin = Member(
            memberName = "사감선생님",
            stuNum = "0000",
            email = "s00000@gsm.hs.kr",
            gender = Gender.PENDING,
            password = passwordEncoder.encode("string"),
            roles = mutableListOf(Role.ROLE_ADMIN),
            ruleViolation = mutableListOf()
        )
        memberRepository.save(admin)

        val developer = Member(
            memberName = "도토리개발자",
            stuNum = "0001",
            email = "s00001@gsm.hs.kr",
            gender = Gender.PENDING,
            password = passwordEncoder.encode("string"),
            roles = mutableListOf(Role.ROLE_DEVELOPER),
            ruleViolation = mutableListOf()
        )
        memberRepository.save(developer)

        val councillor = Member(
            memberName = "기숙사자치위원",
            stuNum = "0002",
            email = "s00002@gsm.hs.kr",
            gender = Gender.PENDING,
            password = passwordEncoder.encode("string"),
            roles = mutableListOf(Role.ROLE_COUNCILLOR),
            ruleViolation = mutableListOf()
        )
        memberRepository.save(councillor)

        val man = Member(
            memberName = "남성유저",
            stuNum = "3101",
            email = "s00003@gsm.hs.kr",
            gender = Gender.MAN,
            password = passwordEncoder.encode("string"),
            roles = mutableListOf(Role.ROLE_DEVELOPER),
            ruleViolation = mutableListOf()
        )
        memberRepository.save(man)

        val woman = Member(
            memberName = "여성유저",
            stuNum = "3201",
            email = "s00004@gsm.hs.kr",
            gender = Gender.MAN,
            password = passwordEncoder.encode("string"),
            roles = mutableListOf(Role.ROLE_DEVELOPER),
            ruleViolation = mutableListOf()
        )
        memberRepository.save(woman)
    }
}