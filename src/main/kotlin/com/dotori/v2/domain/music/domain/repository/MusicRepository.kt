package com.dotori.v2.domain.music.domain.repository

import com.dotori.v2.domain.music.domain.entity.Music
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MusicRepository : JpaRepository<Music, Long> {
    @Query("update Member member set member.musicStatus = 'CAN'")
    fun updateMusicStatusMemberByMember()
}