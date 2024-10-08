package com.dotori.v2.domain.music.domain.repository

import com.dotori.v2.domain.music.domain.entity.MusicLike
import io.lettuce.core.dynamic.annotation.Param
import javax.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface MusicLikeRepository : JpaRepository<MusicLike,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select l from MusicLike l where l.memberId = :memberId and l.musicId = :musicId")
    fun findByMemberIdAndMusicId(@Param("memberId") memberId: Long,@Param("musicId") musicId: Long): MusicLike?

    fun deleteAllByMusicId(music: Long)

    fun deleteAllByMusicIdIn(musicIds: List<Long>)

    fun existsByMusicIdAndMemberId(musicId: Long, memberId: Long): Boolean
}