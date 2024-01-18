package com.dotori.v2.domain.selfstudy.util

import com.dotori.v2.domain.selfstudy.domain.entity.SelfStudyCount
import com.dotori.v2.domain.selfstudy.domain.repository.SelfStudyCountRepository
import org.springframework.stereotype.Component

@Component
class FindSelfStudyCountUtil(
    private val selfStudyCountRepository: SelfStudyCountRepository
) {
    fun findSelfStudyCount(): SelfStudyCount =
        selfStudyCountRepository.findSelfStudyCountById(1L)
}