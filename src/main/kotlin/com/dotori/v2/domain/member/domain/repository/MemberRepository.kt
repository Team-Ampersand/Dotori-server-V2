package com.dotori.v2.domain.member.domain.repository

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.SelfStudyStatus
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
    fun findByEmail(email: String?): Member?
    fun existsByEmail(email: String): Boolean
    fun findAllBySelfStudyCheck(check: Boolean): List<Member>
    fun findAllBySelfStudyStatus(selfStudyStatus: SelfStudyStatus): List<Member>
}