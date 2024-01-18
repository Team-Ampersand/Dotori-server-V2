package com.dotori.v2.domain.music.service.impl

import com.dotori.v2.domain.member.enums.MusicStatus
import com.dotori.v2.domain.music.domain.entity.Music
import com.dotori.v2.domain.music.domain.repository.MusicRepository
import com.dotori.v2.domain.music.exception.MusicNotFoundException
import com.dotori.v2.domain.music.service.DeleteMusicService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class DeleteMusicServiceImpl(
    private val musicRepository: MusicRepository,
) : DeleteMusicService {
    override fun execute(musicId: Long) {
        val music: Music = musicRepository.findByIdOrNull(musicId) ?: throw MusicNotFoundException()

        musicRepository.delete(music)
        music.member.updateMusicStatus(MusicStatus.CAN)
    }
}