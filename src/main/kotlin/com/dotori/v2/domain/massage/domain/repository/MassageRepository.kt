package com.dotori.v2.domain.massage.domain.repository

import com.dotori.v2.domain.massage.domain.entity.Massage
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.self_study.domain.entity.SelfStudy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MassageRepository : JpaRepository<Massage, Long>{
    fun deleteByMember(member: Member)
    @Query("select massage from Massage massage order by massage.createdDate asc")
    fun findAllOrderByCreatedDateAsc(): List<SelfStudy>
}