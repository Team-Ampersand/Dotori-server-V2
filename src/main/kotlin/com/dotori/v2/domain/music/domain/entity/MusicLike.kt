package com.dotori.v2.domain.music.domain.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "likes")
class MusicLike (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    val id: Long = 0,

    @Column(name = "music_id", nullable = false)
    val musicId: Long,

    @Column(name = "member_id", nullable = false)
    val memberId: Long,
) {
    
}