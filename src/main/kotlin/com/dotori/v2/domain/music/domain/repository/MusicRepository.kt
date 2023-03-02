package com.dotori.v2.domain.music.domain.repository

import com.dotori.v2.domain.music.domain.entity.Music
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface MusicRepository : JpaRepository<Music, Long> {
    @Modifying
    @Query("update Member member set member.member_music_status = 'CAN'", nativeQuery = true)
    fun updateMusicStatusMemberByMember()

    fun findAllByCreatedDate(data: LocalDateTime): List<Music>
}