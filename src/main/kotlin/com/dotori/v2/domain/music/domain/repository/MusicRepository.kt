package com.dotori.v2.domain.music.domain.repository

import com.dotori.v2.domain.music.domain.entity.Music
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface MusicRepository : JpaRepository<Music, Long> {
    @Modifying
    @Query("update Member member set member.member_music_status = 'CAN'", nativeQuery = true)
    fun updateMusicStatusMemberByMember()

    @Query(value = "select * from Music music where music.created_date like :date%", nativeQuery = true)
    fun findAllByCreatedDate(@Param("date") date: LocalDate): List<Music>
}