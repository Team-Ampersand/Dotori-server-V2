package com.dotori.v2.domain.board.domain.repository

import com.dotori.v2.domain.board.domain.entity.Board
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Long> {
    fun findAllByOrderByCreatedDateDesc(): List<Board>
}