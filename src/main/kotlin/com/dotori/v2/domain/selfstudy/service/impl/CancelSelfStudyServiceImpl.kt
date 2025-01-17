package com.dotori.v2.domain.selfstudy.service.impl

import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.selfstudy.domain.repository.SelfStudyRepository
import com.dotori.v2.domain.selfstudy.service.CancelSelfStudyService
import com.dotori.v2.domain.selfstudy.util.FindSelfStudyCountUtil
import com.dotori.v2.domain.selfstudy.util.SelfStudyCheckUtil
import com.dotori.v2.domain.selfstudy.util.ValidDayOfWeekAndHourUtil
import com.dotori.v2.domain.student.presentation.data.res.FindAllStudentListResDto
import com.dotori.v2.global.config.redis.service.RedisCacheService
import com.dotori.v2.global.squirrel.ReserveDotoriEvent
import com.dotori.v2.global.util.UserUtil
import org.springframework.context.ApplicationEventPublisher
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(rollbackFor = [Exception::class])
class CancelSelfStudyServiceImpl(
    private val userUtil: UserUtil,
    private val validDayOfWeekAndHourUtil: ValidDayOfWeekAndHourUtil,
    private val findSelfStudyCountUtil: FindSelfStudyCountUtil,
    private val selfStudyRepository: SelfStudyRepository,
    private val selfStudyCheckUtil: SelfStudyCheckUtil,
    private val redisCacheService: RedisCacheService,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val env: Environment
) : CancelSelfStudyService {

    private val CACHE_KEY = "memberList"

    override fun execute() {
        validDayOfWeekAndHourUtil.validateCancel()

        val findSelfStudyCount = findSelfStudyCountUtil.findSelfStudyCount()

        val member = userUtil.fetchCurrentUser()

        selfStudyCheckUtil.isSelfStudyStatusApplied(member)

        member.updateSelfStudyStatus(SelfStudyStatus.CANT)

        selfStudyRepository.deleteByMember(member)
        findSelfStudyCount.removeCount()
        initCache()
        applicationEventPublisher.publishEvent(
            ReserveDotoriEvent.ofCreateSelfStudyEvent(
                username = member.memberName,
                createdAt = LocalDateTime.now(),
                env = env.activeProfiles[0]
            )
        )
    }

    private fun initCache() {
        val cachedData =
            redisCacheService.getFromCache(CACHE_KEY) as? FindAllStudentListResDto

        if (cachedData != null) {
            redisCacheService.deleteFromCache(CACHE_KEY)
        }
    }
}