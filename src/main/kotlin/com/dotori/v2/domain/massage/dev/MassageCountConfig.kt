package com.dotori.v2.domain.massage.dev

import com.dotori.v2.domain.massage.domain.entity.MassageCount
import com.dotori.v2.domain.massage.domain.repository.MassageCountRepository
import com.dotori.v2.domain.massage.domain.repository.MassageRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MassageCountConfig(
    private val massageRepository: MassageRepository,
    private val massageCountRepository: MassageCountRepository,
) {
    private val log = LoggerFactory.getLogger(this::class.simpleName)
    @PostConstruct
    fun selfStudyCountEntitySetting() {
        massageCountRepository.deleteAll()
        massageCountRepository.save(
            MassageCount(
                id = 1L,
                count = massageRepository.count()
            )
        )
        log.info("========== MassageCount Setting Success ==========")
    }
}