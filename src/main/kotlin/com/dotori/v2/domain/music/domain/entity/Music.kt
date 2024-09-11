package com.dotori.v2.domain.music.domain.entity

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.global.entity.BaseTimeEntity
import javax.persistence.*

@Entity
@Table(name = "music")
class Music(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    val id: Long = 0,

    @Column(name = "music_url", nullable = false)
    val url: String,

    @Column(name = "music_title", nullable = false)
    val title: String,

    @Column(name = "music_thumbnail", nullable = false)
    val thumbnail: String,

    @Column(name = "like_count", nullable = false, columnDefinition = "INT default 0")
    var likeCount: Int = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member
) : BaseTimeEntity() {
    fun plusLikeCount() {
        this.likeCount++
    }

    fun minusLikeCount() {
        this.likeCount--
    }
}