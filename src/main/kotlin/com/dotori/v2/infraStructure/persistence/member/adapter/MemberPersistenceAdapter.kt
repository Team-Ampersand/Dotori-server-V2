package com.dotori.v2.infraStructure.persistence.member.adapter

import com.dotori.v2.application.member.port.MemberPort
import com.dotori.v2.infraStructure.persistence.member.repository.MemberRepository
import org.springframework.stereotype.Component

@Component
class MemberPersistenceAdapter(
    private val memberRepository: MemberRepository,
):MemberPort {
    override fun isExist(email: String): Boolean =
        memberRepository.existsByEmail(email)
}