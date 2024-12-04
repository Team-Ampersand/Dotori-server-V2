package com.dotori.v2.domain.selfstudy.service.impl

import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.selfstudy.exception.SelfStudyOverException
import com.dotori.v2.domain.selfstudy.service.ApplySelfStudyService
import com.dotori.v2.domain.selfstudy.util.FindSelfStudyCountUtil
import com.dotori.v2.domain.selfstudy.util.SaveSelfStudyUtil
import com.dotori.v2.domain.selfstudy.util.SelfStudyCheckUtil
import com.dotori.v2.domain.selfstudy.util.ValidDayOfWeekAndHourUtil
import com.dotori.v2.domain.student.presentation.data.res.FindAllStudentListResDto
import com.dotori.v2.global.config.redis.service.RedisCacheService
import com.dotori.v2.global.squirrel.ReserveDotoriEvent
import com.dotori.v2.global.util.UserUtil
import com.dotori.v2.indicator.IndicatorTarget
import org.springframework.context.ApplicationEventPublisher
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(rollbackFor = [Exception::class])
class ApplySelfStudyServiceImpl(
    private val userUtil: UserUtil,
    private val findSelfStudyCountUtil: FindSelfStudyCountUtil,
    private val selfStudyCheckUtil: SelfStudyCheckUtil,
    private val saveSelfStudyUtil: SaveSelfStudyUtil,
    private val validDayOfWeekAndHourUtil: ValidDayOfWeekAndHourUtil,
    private val redisCacheService: RedisCacheService,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val env: Environment
) : ApplySelfStudyService{

    private val CACHE_KEY = "memberList"

    @IndicatorTarget
    override fun execute() {
        validDayOfWeekAndHourUtil.validateApply()

        val member = userUtil.fetchCurrentUser()

        val selfStudyCount = findSelfStudyCountUtil.findSelfStudyCount()

        if (selfStudyCount.count >= selfStudyCount.limit)
            throw SelfStudyOverException()

        selfStudyCheckUtil.isSelfStudyStatusCan(member)
        member.updateSelfStudyStatus(SelfStudyStatus.APPLIED)
        selfStudyCount.addCount()
        saveSelfStudyUtil.save(member)
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