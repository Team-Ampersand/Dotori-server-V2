package com.dotori.v2.domain.self_study.domain.repository

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.self_study.domain.entity.SelfStudy
import org.springframework.data.jpa.repository.JpaRepository

interface SelfStudyRepository : JpaRepository<SelfStudy, Long> {
    fun deleteByMember(member: Member)
}