package com.dotori.v2.domain.self_study.domain.repository

import com.dotori.v2.domain.self_study.domain.entity.SelfStudyCount
import org.springframework.data.jpa.repository.JpaRepository

interface SelfStudyCountRepository : JpaRepository<SelfStudyCount, Long> {
}