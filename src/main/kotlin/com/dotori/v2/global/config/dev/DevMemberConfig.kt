package com.dotori.v2.global.config.dev

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
@Profile("dev")
class DevMemberConfig(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @PostConstruct
    fun generateMember() {
        val password = passwordEncoder.encode("string1!")!!
        if (!memberRepository.existsByEmail("s00000@gsm.hs.kr")) {
            val admin = Member(
                memberName = "사감선생님",
                stuNum = "0000",
                email = "s00000@gsm.hs.kr",
                password = password,
                gender = Gender.PENDING,
                roles = mutableListOf(Role.ROLE_ADMIN),
                ruleViolation = mutableListOf(),
                profileImage = null
            )
            memberRepository.save(admin)
        }

        if (!memberRepository.existsByEmail("s00001@gsm.hs.kr")) {
            val developer = Member(
                memberName = "도토리개발자",
                stuNum = "0001",
                email = "s00001@gsm.hs.kr",
                password = password,
                gender = Gender.PENDING,
                roles = mutableListOf(Role.ROLE_DEVELOPER),
                ruleViolation = mutableListOf(),
                profileImage = null
            )
            memberRepository.save(developer)
        }

        if (!memberRepository.existsByEmail("s00002@gsm.hs.kr")) {
            val councillor = Member(
                memberName = "기숙사자치위원",
                stuNum = "0002",
                email = "s00002@gsm.hs.kr",
                password = password,
                gender = Gender.PENDING,
                roles = mutableListOf(Role.ROLE_COUNCILLOR),
                ruleViolation = mutableListOf(),
                profileImage = null
            )
            memberRepository.save(councillor)
        }

        if (!memberRepository.existsByEmail("s00003@gsm.hs.kr")) {
            val man = Member(
                memberName = "남성유저",
                stuNum = "3101",
                email = "s00003@gsm.hs.kr",
                password = password,
                gender = Gender.PENDING,
                roles = mutableListOf(Role.ROLE_MEMBER),
                ruleViolation = mutableListOf(),
                profileImage = null
            )
            memberRepository.save(man)
        }

        if (!memberRepository.existsByEmail("s00004@gsm.hs.kr")) {
            val woman = Member(
                memberName = "여성유저",
                stuNum = "3201",
                email = "s00004@gsm.hs.kr",
                password = password,
                gender = Gender.PENDING,
                roles = mutableListOf(Role.ROLE_MEMBER),
                ruleViolation = mutableListOf(),
                profileImage = null
            )
            memberRepository.save(woman)
        }
    }
}
