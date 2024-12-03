package com.dotori.v2.domain.music.domain.repository

import com.dotori.v2.domain.music.domain.entity.Music
import javax.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate
import java.time.LocalDateTime

interface MusicRepository : JpaRepository<Music, Long> {
    @Modifying
    @Query("update member set member_music = 'CAN'", nativeQuery = true)
    fun updateMusicStatusMemberByMember()

    @Query(value = "select * from music where created_date like :date%", nativeQuery = true)
    fun findAllByCreatedDate(@Param("date") date: LocalDate): List<Music>

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT m FROM Music m WHERE m.id = :id")
    fun findByIdForUpdate(id: Long): Music?

    fun deleteAllByCreatedDateBefore(date: LocalDateTime)

    fun findAllByCreatedDateBefore(date: LocalDateTime): List<Music>

    @Query(value = "select * from music where created_date like :date% order by music.like_count desc", nativeQuery = true)
    fun findAllByCreatedDateOrderByLikeCountDESC(@Param("date") date: LocalDate): List<Music>
}