package com.dotori.v2.domain.massage.domain.repository

import com.dotori.v2.domain.massage.domain.entity.MassageCount
import org.springframework.data.jpa.repository.JpaRepository

interface MassageCountRepository : JpaRepository<MassageCount, Long> {
}