package com.dotori.v2.domain.massage.domain.repository

import com.dotori.v2.domain.massage.domain.entity.Massage
import com.dotori.v2.domain.member.domain.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MassageRepository : JpaRepository<Massage, Long>{
    fun deleteByMember(member: Member)
}