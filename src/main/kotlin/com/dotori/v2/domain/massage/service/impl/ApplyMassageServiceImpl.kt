package com.dotori.v2.domain.massage.service.impl

import com.dotori.v2.domain.massage.exception.MassageOverException
import com.dotori.v2.domain.massage.service.ApplyMassageService
import com.dotori.v2.domain.massage.util.FindMassageCountUtil
import com.dotori.v2.domain.massage.util.MassageCheckUtil
import com.dotori.v2.domain.massage.util.SaveMassageUtil
import com.dotori.v2.domain.massage.util.ValidDayOfWeekAndHourMassageUtil
import com.dotori.v2.domain.member.enums.MassageStatus
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
@Transactional(rollbackFor = [Exception::class], isolation = Isolation.SERIALIZABLE)
class ApplyMassageServiceImpl(
    private val userUtil: UserUtil,
    private val validDayOfWeekAndHourMassageUtil: ValidDayOfWeekAndHourMassageUtil,
    private val findMassageCountUtil: FindMassageCountUtil,
    private val saveMassageUtil: SaveMassageUtil,
    private val massageCheckUtil: MassageCheckUtil,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val env: Environment
) : ApplyMassageService {

    @IndicatorTarget
    override fun execute() {
        validDayOfWeekAndHourMassageUtil.validateApply()

        val member = userUtil.fetchCurrentUser()
        val massageCount = findMassageCountUtil.findMassageCount()

        if (massageCount.count >= massageCount.limit)
            throw MassageOverException()

        massageCheckUtil.isMassageStatusCan(member)
        member.updateMassageStatus(MassageStatus.APPLIED)
        massageCount.addCount()
        saveMassageUtil.save(member)

        applicationEventPublisher.publishEvent(
            ReserveDotoriEvent.ofCreateMassageEvent(
                member.memberName,
                createdAt = LocalDateTime.now(),
                env = env.activeProfiles[0]
            )
        )
    }
}