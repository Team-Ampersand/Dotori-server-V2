package com.dotori.v2.domain.board.domain.repository

import com.dotori.v2.domain.board.domain.entity.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BoardRepository : JpaRepository<Board, Long> {
    @Query("select board from Board board where board.createdDate = (select max(board.createdDate) from Board board)")
    fun findLastBoard(): Board?
}