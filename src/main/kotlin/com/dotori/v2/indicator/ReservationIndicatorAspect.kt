package com.dotori.v2.indicator

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Aspect
@Component
class ReservationIndicatorAspect(
    private val reservationIndicatorsRepository: ReservationIndicatorsRepository
) {

    @Around("@annotation(com.dotori.v2.indicator.Indicate)")
    fun indicateLatency(joinPoint: ProceedingJoinPoint): Any? {
        val currentTime = LocalDateTime.now()
        val startHour = 8
        val endHour = 9

        return if(currentTime.hour in startHour .. endHour) {
            val startMillis = System.currentTimeMillis()

            val userId = SecurityContextHolder.getContext().authentication.name.toLong()
            var result: Any? = null
            var status: ResultStatus
            val reservationCategory: ReservationCategory

            try {
                result = joinPoint.proceed()
                status = ResultStatus.SUCCESS
            } catch(e: BasicException) {
                status = ResultStatus.FAILED
            }

            val endTime = LocalDateTime.now()
            val duration = System.currentTimeMillis() - startMillis
            val signatureName = joinPoint.signature.declaringType.simpleName
            reservationCategory = when (signatureName) {
                "ApplySelfStudyService" -> {
                    ReservationCategory.SELFSTUDY
                }
                "ApplyMassageService" -> {
                    ReservationCategory.MASSAGE
                }
                else -> {
                    throw BasicException(ErrorCode.UNKNOWN_ERROR)
                }
            }

            val reservationIndicators = ReservationIndicators(
                userId = userId,
                requestTime = currentTime,
                responseTime = endTime,
                latencyMs = duration,
                resultStatus = status,
                reservationCategory = reservationCategory
            )

            reservationIndicatorsRepository.save(reservationIndicators)
            result
        } else {
            joinPoint.proceed()
        }
    }

}