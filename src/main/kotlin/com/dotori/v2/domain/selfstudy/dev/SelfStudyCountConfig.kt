package com.dotori.v2.domain.selfstudy.dev

import com.dotori.v2.domain.selfstudy.domain.entity.SelfStudyCount
import com.dotori.v2.domain.selfstudy.domain.repository.SelfStudyCountRepository
import com.dotori.v2.domain.selfstudy.domain.repository.SelfStudyRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class SelfStudyCountConfig(
    private val selfStudyRepository: SelfStudyRepository,
    private val selfStudyCountRepository: SelfStudyCountRepository,
) {
    private val log = LoggerFactory.getLogger(this::class.simpleName)
    @PostConstruct
    fun selfStudyCountEntitySetting() {
        selfStudyCountRepository.deleteAll()
        selfStudyCountRepository.save(
            SelfStudyCount(
                id = 1L,
                count = selfStudyRepository.count()
            )
        )
        log.info("========== SelfStudyCount Setting Success ==========")
    }
}