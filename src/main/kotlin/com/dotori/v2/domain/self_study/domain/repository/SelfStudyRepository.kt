package com.dotori.v2.domain.self_study.domain.repository

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.self_study.domain.entity.SelfStudy
import kotlinx.coroutines.selects.select
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SelfStudyRepository : JpaRepository<SelfStudy, Long> {
    fun deleteByMember(member: Member)
    @Query("select selfStudy from SelfStudy selfStudy order by selfStudy.createdDate asc")
    fun findAllOrderByCreatedDateAsc(): List<SelfStudy>
}