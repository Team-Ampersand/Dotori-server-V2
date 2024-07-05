package com.dotori.v2.domain.member.domain.repository

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.MassageStatus
import com.dotori.v2.domain.member.enums.MusicStatus
import com.dotori.v2.domain.member.enums.SelfStudyStatus
import javax.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MemberRepository : JpaRepository<Member, Long>, CustomMemberRepository {
    fun save(member: Member): Member
    fun findByEmail(email: String?): Member?
    fun existsByEmail(email: String): Boolean
    fun findByStuNum(stuNum: String): Member?
    fun findAllBySelfStudyCheck(check: Boolean): List<Member>
    fun findAllBySelfStudyStatus(selfStudyStatus: SelfStudyStatus): List<Member>
    fun findAllByMassageStatusOrMassageStatus(first: MassageStatus, second: MassageStatus): List<Member>
    fun findAllByMemberNameStartingWithOrderByStuNumAsc(memberName: String): List<Member>
    fun findAllByOrderByStuNumAsc(): List<Member>
    fun findAllByStuNum(stuNum: String): List<Member>
    fun countByStuNum(stuNum: String): Int

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "select m.musicStatus from Member m where m.id = :member_id")
    fun findMusicStatusByMemberId(@Param("member_id") id: Long): MusicStatus
}