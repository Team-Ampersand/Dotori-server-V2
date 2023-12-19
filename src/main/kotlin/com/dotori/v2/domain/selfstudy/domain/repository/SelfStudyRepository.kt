package com.dotori.v2.domain.selfstudy.domain.repository

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.selfstudy.domain.entity.SelfStudy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SelfStudyRepository : JpaRepository<SelfStudy, Long> {
    fun deleteByMember(member: Member)
    @Query("select selfStudy from SelfStudy selfStudy order by selfStudy.createdDate asc")
    fun findAllOrderByCreatedDateAsc(): List<SelfStudy>
    @Query("select selfStudy.member from SelfStudy selfStudy where selfStudy.member.memberName like %:memberName%")
    fun findAllByMemberName(@Param("memberName")memberName: String): List<Member>
    @Query("select selfStudy.member from SelfStudy selfStudy where selfStudy.member.stuNum like :stuNum%")
    fun findAllByStuNum(@Param("stuNum") stuNum: String): List<Member>
    fun findByMemberIn(member: List<Member>): List<SelfStudy>
    fun existsByMember(member: Member): Boolean
}