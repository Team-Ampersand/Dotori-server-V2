package com.dotori.v2.domain.like.repository

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.like.entity.Like
import com.dotori.v2.domain.music.domain.entity.Music
import feign.Param
import javax.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface LikeRepository : JpaRepository<Like, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select l from Like l where l.music = :music and l.member = :member")
    fun findByMemberAndMusic(@Param("member") member: Member, @Param("music") music: Music): Like?
}