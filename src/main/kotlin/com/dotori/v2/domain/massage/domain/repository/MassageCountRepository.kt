package com.dotori.v2.domain.massage.domain.repository

import com.dotori.v2.domain.massage.domain.entity.MassageCount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import javax.persistence.LockModeType

interface MassageCountRepository : JpaRepository<MassageCount, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findMassageCountById(id: Long): MassageCount
}