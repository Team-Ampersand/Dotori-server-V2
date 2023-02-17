package com.dotori.v2.domain.music.domain.repository

import com.dotori.v2.domain.music.domain.entity.Music
import org.springframework.data.jpa.repository.JpaRepository

interface MusicRepository : JpaRepository<Music, Long> {
}