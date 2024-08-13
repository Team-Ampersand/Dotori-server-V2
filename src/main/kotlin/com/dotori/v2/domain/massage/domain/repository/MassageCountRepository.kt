package com.dotori.v2.domain.massage.domain.repository

import com.dotori.v2.domain.massage.domain.entity.MassageCount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import javax.persistence.LockModeType
import org.springframework.data.jpa.repository.Query

interface MassageCountRepository : JpaRepository<MassageCount, Long> {
    fun findMassageCountById(id: Long): MassageCount

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select m from MassageCount m where m.id = :id")
    fun findMassageCountByIdForUpdate(id: Long): MassageCount
}