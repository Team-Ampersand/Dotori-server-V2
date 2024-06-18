package com.dotori.v2.domain.music.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.MusicStatus
import com.dotori.v2.domain.music.domain.entity.Music
import com.dotori.v2.domain.music.domain.repository.MusicRepository
import com.dotori.v2.domain.music.exception.MusicAlreadyException
import com.dotori.v2.domain.music.exception.MusicCantRequestDateException
import com.dotori.v2.domain.music.presentation.data.dto.ApplyMusicDto
import com.dotori.v2.domain.music.presentation.data.req.ApplyMusicReqDto
import com.dotori.v2.domain.music.service.ApplyMusicService
import com.dotori.v2.global.thirdparty.youtube.service.YoutubeService
import com.dotori.v2.global.thirdparty.youtube.data.res.YoutubeResDto
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek

@Service
@Transactional(rollbackFor = [Exception::class])
class ApplyMusicServiceImpl(
    private val userUtil: UserUtil,
    private val musicRepository: MusicRepository,
    private val youtubeService: YoutubeService,
    private val memberRepository: MemberRepository
) : ApplyMusicService {
    override fun execute(applyMusicReqDto: ApplyMusicReqDto, dayOfWeek: DayOfWeek): Music {
        validDayOfWeek(dayOfWeek)

        val memberInfo: Member = userUtil.fetchCurrentUser()

        isCanApplyMusicStatus(memberInfo)

        val youtubeInfo = youtubeService.getYoutubeInfo(applyMusicReqDto.url)
        val music: Music = toDto(applyMusicReqDto)
            .let { musicRepository.save(toEntity(it, memberInfo, youtubeInfo)) }
        memberInfo.updateMusicStatus(MusicStatus.APPLIED)

        return music
    }

    private fun toDto(applyMusicReqDto: ApplyMusicReqDto): ApplyMusicDto =
        ApplyMusicDto(
            url = applyMusicReqDto.url
        )

    private fun validDayOfWeek(dayOfWeek: DayOfWeek) {
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY)
            throw MusicCantRequestDateException()
    }

    private fun isCanApplyMusicStatus(member: Member) {
        if (memberRepository.findMemberByMusicStatus(member.id) != MusicStatus.CAN) throw MusicAlreadyException()
    }

    private fun toEntity(applyMusicDto: ApplyMusicDto, member: Member, youtubeResDto: YoutubeResDto): Music =
        Music(
            url = applyMusicDto.url,
            member = member,
            title = youtubeResDto.title,
            thumbnail = youtubeResDto.thumbnail
        )
}