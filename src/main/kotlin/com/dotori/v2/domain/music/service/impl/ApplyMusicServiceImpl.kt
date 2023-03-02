package com.dotori.v2.domain.music.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.MusicStatus
import com.dotori.v2.domain.music.domain.entity.Music
import com.dotori.v2.domain.music.domain.repository.MusicRepository
import com.dotori.v2.domain.music.exception.MusicAlreadyException
import com.dotori.v2.domain.music.exception.MusicCantRequestDateException
import com.dotori.v2.domain.music.presentation.data.dto.ApplyMusicDto
import com.dotori.v2.domain.music.presentation.data.req.ApplyMusicReqDto
import com.dotori.v2.domain.music.service.ApplyMusicService
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek

@Service
@Transactional(rollbackFor = [Exception::class])
class ApplyMusicServiceImpl(
    private val userUtil: UserUtil,
    private val musicRepository: MusicRepository
) : ApplyMusicService {
    override fun execute(applyMusicReqDto: ApplyMusicReqDto, dayOfWeek: DayOfWeek): Music {
        validDayOfWeek(dayOfWeek)
        val memberInfo: Member = userUtil.fetchCurrentUser()
        isCanApplyMusicStatus(memberInfo)
        val music: Music = toDto(applyMusicReqDto)
            .let { musicRepository.save(toEntity(it, memberInfo)) }
        memberInfo.updateMusicStatus(MusicStatus.APPLIED)
        return music
    }

    private fun toDto(applyMusicReqDto: ApplyMusicReqDto): ApplyMusicDto =
        ApplyMusicDto(
            url = applyMusicReqDto.url
        )

    private fun validDayOfWeek(dayOfWeek: DayOfWeek) {
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
            throw MusicCantRequestDateException()
    }

    private fun isCanApplyMusicStatus(member: Member) {
        if (member.musicStatus != MusicStatus.CAN) throw MusicAlreadyException()
    }

    private fun toEntity(applyMusicDto: ApplyMusicDto, member: Member): Music =
        Music(
            url = applyMusicDto.url,
            member = member
        )
}