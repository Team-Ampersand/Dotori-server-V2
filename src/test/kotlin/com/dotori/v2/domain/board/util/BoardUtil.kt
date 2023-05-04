package com.dotori.v2.domain.board.util

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.domain.entity.BoardImage
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.util.MemberUtil
class BoardUtil {
    companion object {
        fun createBoard(
            id: Long = 0,
            member: Member = MemberUtil.createMember(),
            title: String = "공지사항 테스트",
            content: String = "공지사항 테스트 입니다.",
            boardImage: List<BoardImage> = mutableListOf()
        ): Board = Board(id, member, title, content, boardImage)

        fun createBoardImage(
            id: Long = 0,
            board: Board = createBoard(),
            url: String = "https://www.test.url"
        ) = BoardImage(id, board, url)
    }
}


