package com.dotori.v2.infraStructure.persistence.member

import com.dotori.v2.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
    fun findByEmail(email: String?): Member?
}