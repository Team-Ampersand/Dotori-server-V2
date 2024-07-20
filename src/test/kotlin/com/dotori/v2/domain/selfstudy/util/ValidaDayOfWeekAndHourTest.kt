package com.dotori.v2.domain.selfstudy.util

import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyApplyDayException
import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyApplyHourException
import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyCancelDayException
import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyCancelHourException
import com.dotori.v2.domain.selfstudy.properties.SelfStudyProperties
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import java.time.LocalDateTime
import org.springframework.core.env.Environment

class ValidDayOfWeekAndHourTest : BehaviorSpec({
    mockkStatic(LocalDateTime::class)

    // prod 프로파일에 대한 테스트 케이스
    given("신청가능한 날짜가 주어지고 prod 프로파일") {
        val validDayOfWeekAndHourUtil = validDayOfWeekAndHourUtil(2023, 4, 27, 20, 0, 1, "prod")
        `when`("신청가능한지 검증할 때") {
            then("아무 예외도 발생하지 않아야 함") {
                validDayOfWeekAndHourUtil.validateApply() shouldBe Unit
            }
        }
        `when`("신청취소가 가능한지 검증할 때") {
            then("아무 예외도 발생하지 않아야 함") {
                validDayOfWeekAndHourUtil.validateCancel()
            }
        }
    }
    given("시간이 오후 8시가 아닌 날짜가 주어지면 prod 프로파일") {
        val validDayOfWeekAndHourUtil = validDayOfWeekAndHourUtil(2023, 4, 27, 19, 0, 0, "prod")
        `when`("신청가능한지 검증할 때") {
            then("NotSelfStudyApplyHourException이 발생해야 함") {
                shouldThrow<NotSelfStudyApplyHourException> {
                    validDayOfWeekAndHourUtil.validateApply()
                }
            }
        }
        `when`("신청취소가 가능한지 검증할 때") {
            then("NotSelfStudyCancelHourException이 발생해야 함") {
                shouldThrow<NotSelfStudyCancelHourException> {
                    validDayOfWeekAndHourUtil.validateCancel()
                }
            }
        }
    }
    given("날짜가 금요일이나 주말인 날짜가 주어지면 prod 프로파일") {
        var validDayOfWeekAndHourUtil = validDayOfWeekAndHourUtil(2023, 4, 28, 20, 0, 0, "prod")
        `when`("금요일에 신청가능한지 검증할 때") {
            then("NotSelfStudyApplyDayException이 발생해야 함") {
                shouldThrow<NotSelfStudyApplyDayException> {
                    validDayOfWeekAndHourUtil.validateApply()
                }
            }
        }
        `when`("금요일에 신청취소가 가능한지 검증할 때") {
            then("NotSelfStudyCancelDayException이 발생해야 함") {
                shouldThrow<NotSelfStudyCancelDayException> {
                    validDayOfWeekAndHourUtil.validateCancel()
                }
            }
        }
        validDayOfWeekAndHourUtil = validDayOfWeekAndHourUtil(2023, 4, 29, 20, 0, 0, "prod")
        `when`("토요일에 신청가능한지 검증할 때") {
            then("NotSelfStudyApplyDayException이 발생해야 함") {
                shouldThrow<NotSelfStudyApplyDayException> {
                    validDayOfWeekAndHourUtil.validateApply()
                }
            }
        }
        `when`("토요일에 신청취소가 가능한지 검증할 때") {
            then("NotSelfStudyCancelDayException이 발생해야 함") {
                shouldThrow<NotSelfStudyCancelDayException> {
                    validDayOfWeekAndHourUtil.validateCancel()
                }
            }
        }
    }

    // dev 프로파일에 대한 테스트 케이스
    given("신청가능한 날짜가 주어지고 dev 프로파일") {
        val validDayOfWeekAndHourUtil = validDayOfWeekAndHourUtil(2023, 4, 27, 20, 0, 1, "dev")
        `when`("신청가능한지 검증할 때") {
            then("아무 예외도 발생하지 않아야 함") {
                validDayOfWeekAndHourUtil.validateApply() shouldBe Unit
            }
        }
        `when`("신청취소가 가능한지 검증할 때") {
            then("아무 예외도 발생하지 않아야 함") {
                validDayOfWeekAndHourUtil.validateCancel()
            }
        }
    }
    given("시간이 오후 8시가 아닌 날짜가 주어지면 dev 프로파일") {
        val validDayOfWeekAndHourUtil = validDayOfWeekAndHourUtil(2023, 4, 27, 19, 0, 0, "dev")
        `when`("신청가능한지 검증할 때"){
            then("아무 예외도 발생하지 않아야 함") {
                validDayOfWeekAndHourUtil.validateApply() shouldBe Unit
            }
        }
        `when`("신청취소가 가능한지 검증할 때") {
            then("아무 예외도 발생하지 않아야 함") {
                validDayOfWeekAndHourUtil.validateCancel()
            }
        }
    }
    given("날짜가 금요일이나 주말인 날짜가 주어지면 dev 프로파일") {
        var validDayOfWeekAndHourUtil = validDayOfWeekAndHourUtil(2023, 4, 28, 20, 0, 0, "dev")
        `when`("금요일에 신청가능한지 검증할 때") {
            then("아무 예외도 발생하지 않아야 함") {
                validDayOfWeekAndHourUtil.validateApply() shouldBe Unit
            }
        }
        `when`("금요일에 신청취소가 가능한지 검증할 때") {
            then("아무 예외도 발생하지 않아야 함") {
                validDayOfWeekAndHourUtil.validateCancel()
            }
        }
        validDayOfWeekAndHourUtil = validDayOfWeekAndHourUtil(2023, 4, 29, 20, 0, 0, "dev")
        `when`("토요일에 신청가능한지 검증할 때") {
            then("아무 예외도 발생하지 않아야 함") {
                validDayOfWeekAndHourUtil.validateApply() shouldBe Unit
            }
        }
        `when`("토요일에 신청취소가 가능한지 검증할 때") {
            then("아무 예외도 발생하지 않아야 함") {
                validDayOfWeekAndHourUtil.validateCancel()
            }
        }
    }
})

private fun validDayOfWeekAndHourUtil(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, profile: String): ValidDayOfWeekAndHourUtil {
    val testDate = LocalDateTime.of(year, month, day, hour, minute, second)
    val selfStudyProperties = mockk<SelfStudyProperties>()
    val environment = mockk<Environment>()

    every { environment.activeProfiles } returns arrayOf(profile)

    if (profile == "dev") {
        every { selfStudyProperties.allowedStartTime } returns "00:00"
        every { selfStudyProperties.allowedEndTime } returns "23:59"
    } else if (profile == "prod") {
        every { selfStudyProperties.allowedStartTime } returns "20:00"
        every { selfStudyProperties.allowedEndTime } returns "20:59"
    }

    every { LocalDateTime.now() } returns testDate

    return ValidDayOfWeekAndHourUtil(selfStudyProperties, environment)
}