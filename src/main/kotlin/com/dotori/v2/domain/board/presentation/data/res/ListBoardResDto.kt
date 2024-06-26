package com.dotori.v2.domain.board.presentation.data.res

data class ListBoardResDto(
    val boardList: List<BoardResDto>
) {
    constructor() : this(listOf())
}
