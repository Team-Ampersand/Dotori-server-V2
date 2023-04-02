package com.dotori.v2.domain.board.domain.repository

import com.dotori.v2.domain.board.domain.entity.Board
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface BoardRepository : JpaRepository<Board, Long> {
    fun findAllByOrderByCreatedDateDesc(): List<Board>
}