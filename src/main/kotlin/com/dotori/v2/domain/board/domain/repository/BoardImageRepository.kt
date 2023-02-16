package com.dotori.v2.domain.board.domain.repository

import com.dotori.v2.domain.board.domain.entity.BoardImage
import org.springframework.data.jpa.repository.JpaRepository

interface BoardImageRepository : JpaRepository<BoardImage, Long>{
}