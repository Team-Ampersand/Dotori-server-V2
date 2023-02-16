package com.dotori.v2.domain.music.domain.entity

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.global.entity.BaseTimeEntity
import javax.persistence.*


@Entity
@Table(name = "Music")
class Music(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    val id: Long = 0,
    @Column(name = "music_url", nullable = false)
    val url: String,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member
) : BaseTimeEntity()