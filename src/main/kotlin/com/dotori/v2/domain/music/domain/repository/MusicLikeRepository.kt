package com.dotori.v2.domain.music.domain.repository

import com.dotori.v2.domain.music.domain.entity.MusicLike
import org.springframework.data.jpa.repository.JpaRepository

interface MusicLikeRepository : JpaRepository<MusicLike,Long> {
    fun findByMemberIdAndMusicId(memberId: Long, musicId: Long): MusicLike?

    fun deleteAllByMusicId(music: Long)

    fun deleteAllByMusicIdIn(musicIds: List<Long>)

    fun existsByMusicIdAndMemberId(musicId: Long, memberId: Long): Boolean
}