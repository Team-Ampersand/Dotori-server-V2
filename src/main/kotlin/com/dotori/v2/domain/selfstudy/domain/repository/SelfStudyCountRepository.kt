package com.dotori.v2.domain.selfstudy.domain.repository

import com.dotori.v2.domain.selfstudy.domain.entity.SelfStudyCount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import javax.persistence.LockModeType


interface SelfStudyCountRepository : JpaRepository<SelfStudyCount, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findSelfStudyCountById(id: Long): SelfStudyCount
}