package com.dotori.v2.domain.selfstudy.domain.repository

import com.dotori.v2.domain.selfstudy.domain.entity.SelfStudyCount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import javax.persistence.LockModeType
import org.springframework.data.jpa.repository.Query


interface SelfStudyCountRepository : JpaRepository<SelfStudyCount, Long> {
    fun findSelfStudyCountById(id: Long): SelfStudyCount

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from SelfStudyCount s where s.id = :id")
    fun findSelfStudyCountByIdForUpdate(id: Long): SelfStudyCount
}