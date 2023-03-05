package com.dotori.v2.domain.massage.util

import com.dotori.v2.domain.massage.domain.entity.MassageCount
import com.dotori.v2.domain.massage.domain.repository.MassageCountRepository
import org.springframework.stereotype.Component

@Component
class FindMassageCountUtil(
    private val massageCountRepository: MassageCountRepository
) {
    fun findSelfStudyCount(): MassageCount =
        massageCountRepository.findMassageCountById(1L)
}