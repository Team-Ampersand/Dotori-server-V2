package com.dotori.v2.domain.member.domain.repository

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.MassageStatus
import com.dotori.v2.domain.member.enums.SelfStudyStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, UUID>, CustomMemberRepository {
    fun findByEmail(email: String?): Member?
    fun existsByEmail(email: String): Boolean
    fun findByStuNum(stuNum: String): Member?
    fun findAllBySelfStudyCheck(check: Boolean): List<Member>
    fun findAllBySelfStudyStatus(selfStudyStatus: SelfStudyStatus): List<Member>
    fun findAllByMassageStatusOrMassageStatus(first: MassageStatus, second: MassageStatus): List<Member>
    fun findAllByMemberNameStartingWithOrderByStuNumAsc(memberName: String): List<Member>
    fun findAllByOrderByStuNumAsc(): List<Member>
}