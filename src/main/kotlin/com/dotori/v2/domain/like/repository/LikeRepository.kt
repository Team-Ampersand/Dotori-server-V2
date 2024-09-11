package com.dotori.v2.domain.like.repository

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.like.entity.Like
import com.dotori.v2.domain.music.domain.entity.Music
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository : JpaRepository<Like,Long> {
    fun findByMember(member: Member): List<Like>

    fun findByMemberAndMusic(member: Member,music: Music): Like
}