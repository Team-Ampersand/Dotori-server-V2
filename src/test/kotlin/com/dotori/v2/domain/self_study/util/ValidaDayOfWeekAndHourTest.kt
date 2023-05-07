package com.dotori.v2.domain.self_study.util

import com.dotori.v2.domain.self_study.exception.NotSelfStudyApplyDayException
import com.dotori.v2.domain.self_study.exception.NotSelfStudyApplyHourException
import com.dotori.v2.domain.self_study.exception.NotSelfStudyCancelDayException
import com.dotori.v2.domain.self_study.exception.NotSelfStudyCancelHourException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class ValidaDayOfWeekAndHourTest : BehaviorSpec({
    given("신청가능한 날짜 주어지고") {
        val validDayOfWeekAndHourUtil = validDayOfWeekAndHourUtil(2023, 4, 27, 20, 0, 1)
        `when`("신청가능한지 검증할때") {
            then("아무 예외도 발생하지 않아야함") {
                validDayOfWeekAndHourUtil.validateApply() shouldBe Unit
            }
        }
        `when`("신청취소가 가능한지 검증할때") {
            then("아무 예외도 발생하지 않아야함") {
                validDayOfWeekAndHourUtil.validateCancel()
            }
        }
    }
    given("시간이 오후 8시가 아닌 날짜가 주어지면") {
        val validDayOfWeekAndHourUtil = validDayOfWeekAndHourUtil(2023, 4, 27, 19, 0, 0)
        `when`("신청가능한지 검증할때") {
            then("NotSelfStudyApplyHourException이 발생해야함") {
                shouldThrow<NotSelfStudyApplyHourException> {
                    validDayOfWeekAndHourUtil.validateApply()
                }
            }
        }
        `when`("신청취소가 가능한지 검증할때") {
            then("NotSelfStudyCancelHourException이 발생해야함") {
                shouldThrow<NotSelfStudyCancelHourException> {
                    validDayOfWeekAndHourUtil.validateCancel()
                }
            }
        }
    }
    given("날짜가 금요일이나 주말인 날짜가 주어지면") {
        var validDayOfWeekAndHourUtil = validDayOfWeekAndHourUtil(2023, 4, 28, 20, 0, 0)
        `when`("금요일에 신청가능한지 검증할때") {
            then("NotSelfStudyApplyHourException이 발생해야함") {
                shouldThrow<NotSelfStudyApplyDayException> {
                    validDayOfWeekAndHourUtil.validateApply()
                }
            }
        }
        `when`("금요일에 신청취소가 가능한지 검증할때") {
            then("NotSelfStudyCancelDayException이 발생해야함") {
                shouldThrow<NotSelfStudyCancelDayException> {
                    validDayOfWeekAndHourUtil.validateCancel()
                }
            }
        }
        validDayOfWeekAndHourUtil = validDayOfWeekAndHourUtil(2023, 4, 29, 20, 0, 0)
        `when`("토요일에 신청가능한지 검증할때") {
            then("NotSelfStudyApplyHourException이 발생해야함") {
                shouldThrow<NotSelfStudyApplyDayException> {
                    validDayOfWeekAndHourUtil.validateApply()
                }
            }
        }
        `when`("토요일에 신청취소가 가능한지 검증할때") {
            then("NotSelfStudyCancelDayException이 발생해야함") {
                shouldThrow<NotSelfStudyCancelDayException> {
                    validDayOfWeekAndHourUtil.validateCancel()
                }
            }
        }
        /*
        validDayOfWeekAndHourUtil = validDayOfWeekAndHourUtil(2023, 4, 30, 20, 0, 0)
        `when`("일요일에 신청가능한지 검증할때") {
            then("NotSelfStudyApplyHourException이 발생해야함") {
                shouldThrow<NotSelfStudyApplyDayException> {
                    validDayOfWeekAndHourUtil.validateApply()
                }
            }
        }
        `when`("일요일에 신청취소가 가능한지 검증할때") {
            then("NotSelfStudyCancelDayException이 발생해야함") {
                shouldThrow<NotSelfStudyCancelDayException> {
                    validDayOfWeekAndHourUtil.validateCancel()
                }
            }
        }*/
    }
})

private fun validDayOfWeekAndHourUtil(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): ValidDayOfWeekAndHourUtil {
    val testDate = LocalDateTime.of(year, month, day, hour, minute, second)
    return ValidDayOfWeekAndHourUtil(testDate)
}