package com.dotori.v2.domain.massage.domain.repository

import com.dotori.v2.domain.massage.domain.entity.Massage
import org.springframework.data.jpa.repository.JpaRepository

interface MassageRepository : JpaRepository<Massage, Long>{
}