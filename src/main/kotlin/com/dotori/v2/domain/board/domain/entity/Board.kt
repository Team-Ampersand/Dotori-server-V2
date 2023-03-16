package com.dotori.v2.domain.board.domain.entity

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.global.entity.BaseTimeEntity
import java.time.LocalDateTime
import java.time.ZonedDateTime
import javax.persistence.*


@Entity
@Table(name = "Board")
class Board(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    val id: Long = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,
    @Column(name = "board_title", nullable = false)
    var title: String,
    @Column(name = "board_content", length = 5000, nullable = false)
    var content: String,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
    val boardImage: List<BoardImage>

) : BaseTimeEntity() {
    fun updateBoard(title: String, content: String) {
        this.title = title
        this.content = content
        this.modifiedDate = LocalDateTime.now()
    }
}