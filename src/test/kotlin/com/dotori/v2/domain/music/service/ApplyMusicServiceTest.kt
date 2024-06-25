package com.dotori.v2.domain.music.service

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.MusicStatus
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.music.domain.entity.Music
import com.dotori.v2.domain.music.domain.repository.MusicRepository
import com.dotori.v2.domain.music.exception.MusicAlreadyException
import com.dotori.v2.domain.music.exception.MusicCantRequestDateException
import com.dotori.v2.domain.music.presentation.data.req.ApplyMusicReqDto
import com.dotori.v2.domain.music.service.impl.ApplyMusicServiceImpl
import com.dotori.v2.global.config.redis.service.RedisCacheService
import com.dotori.v2.global.thirdparty.youtube.data.res.YoutubeResDto
import com.dotori.v2.global.thirdparty.youtube.exception.NotValidUrlException
import com.dotori.v2.global.thirdparty.youtube.service.YoutubeService
import com.dotori.v2.global.util.UserUtil
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.DayOfWeek
import java.util.*


class ApplyMusicServiceTest : BehaviorSpec({
    val userUtil = mockk<UserUtil>()
    val musicRepository = mockk<MusicRepository>()
    val youtubeService = mockk<YoutubeService>()
    val memberRepository = mockk<MemberRepository>()
    val redisCacheService = mockk<RedisCacheService>()
    val applyMusicService = ApplyMusicServiceImpl(userUtil, musicRepository, youtubeService, memberRepository, redisCacheService)

    given("유저가 주어지고") {
        val testMember = Member(
            memberName = "test",
            stuNum = "2402",
            email = "test@gsm.hs.kr",
            password = "password1234",
            gender = Gender.MAN,
            roles = Collections.singletonList(Role.ROLE_MEMBER),
            ruleViolation = mutableListOf(),
            profileImage = null
        )
        val testMusic = Music(
            id = 0L,
            url = "https://www.youtube.com/watch?v=ajeoinsweecmwcssfdkownmsoo",
            member = testMember,
            title = "test",
            thumbnail = "test"
        )
        val testMusic2 = Music(
            id = 1L,
            url = "https://youtu.be/72vIkM5mUVM?si=DFmSX2mjpERPS0tY",
            member = testMember,
            title = "test",
            thumbnail = "test"
        )
        val applyMusicReqDto = ApplyMusicReqDto(
            url = "https://www.youtube.com/watch?v=ajeoinsweecmwcssfdkownmsoo"
        )
        val applyMusicReqDto2 = ApplyMusicReqDto(
            url = "https://youtu.be/72vIkM5mUVM?si=DFmSX2mjpERPS0tY"
        )
        val notValidApplyMusicReqDto = ApplyMusicReqDto(
            url = "sdjfwjeiowow"
        )
        val youtubeResDto = YoutubeResDto(
            title = "test",
            thumbnail = "test"
        )

        every { userUtil.fetchCurrentUser() } returns testMember
        every { youtubeService.getYoutubeInfo(applyMusicReqDto.url) } returns youtubeResDto
        every { musicRepository.save(any()) } returns testMusic
        every { memberRepository.findMusicStatusByMemberId(testMember.id) } returns MusicStatus.CAN

        `when`("applyMusicReqDto으로 요청하면") {
            val result = applyMusicService.execute(applyMusicReqDto, DayOfWeek.THURSDAY)
            then("save가 실행되어야함") {
                verify(exactly = 1) { musicRepository.save(any()) }
            }
            then("유저의 음악신청 상태가 신청된 상태가 되어야함") {
                testMember.musicStatus shouldBe MusicStatus.APPLIED
            }
            then("music이 반환되어야함") {
                result shouldBe testMusic
            }
        }

        every { userUtil.fetchCurrentUser() } returns testMember
        every { youtubeService.getYoutubeInfo(applyMusicReqDto2.url) } returns youtubeResDto
        every { musicRepository.save(any()) } returns testMusic2
        every { memberRepository.findMusicStatusByMemberId(testMember.id) } returns MusicStatus.CAN

        testMember.updateMusicStatus(MusicStatus.CAN)

        `when`("applyMusicReqDto2로 요청하면") {
            val result = applyMusicService.execute(applyMusicReqDto2, DayOfWeek.THURSDAY)
            then("save가 실행되어야함") {
                verify(exactly = 2) { musicRepository.save(any()) }
            }
            then("유저의 음악신청 상태가 신청된 상태가 되어야함") {
                testMember.musicStatus shouldBe MusicStatus.APPLIED
            }
            then("music이 반환되어야함") {
                result shouldBe testMusic2
            }
        }
        `when`("유효하지 않은 날짜로 실행하면") {
            val invalidDay = DayOfWeek.FRIDAY
            shouldThrow<MusicCantRequestDateException> {
                applyMusicService.execute(applyMusicReqDto, invalidDay)
            }
        }
        `when`("유효하지 않은 youtube url 로 요청하면") {
            val invalidDay = DayOfWeek.THURSDAY
            testMember.updateMusicStatus(MusicStatus.CAN)
            every { youtubeService.getYoutubeInfo(notValidApplyMusicReqDto.url) } throws NotValidUrlException()
            shouldThrow<NotValidUrlException> {
                applyMusicService.execute(notValidApplyMusicReqDto, invalidDay)
            }
        }
        `when`("이미 음악신청을 했으면") {
            val invalidDay = DayOfWeek.MONDAY
            every { userUtil.fetchCurrentUser() } returns testMember
            every { memberRepository.findMusicStatusByMemberId(testMember.id) } returns MusicStatus.APPLIED
            shouldThrow<MusicAlreadyException> {
                applyMusicService.execute(applyMusicReqDto, invalidDay)
            }
        }
    }
})