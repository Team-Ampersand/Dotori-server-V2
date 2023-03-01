package com.dotori.v2.domain.self_study.util

import com.dotori.v2.domain.self_study.domain.entity.SelfStudyCount
import com.dotori.v2.domain.self_study.domain.repository.SelfStudyCountRepository
import org.springframework.stereotype.Component

@Component
class FindSelfStudyCountUtil(
    private val selfStudyCountRepository: SelfStudyCountRepository
) {
    fun findSelfStudyCount(): SelfStudyCount =
        selfStudyCountRepository.findSelfStudyCountById(1L)
}