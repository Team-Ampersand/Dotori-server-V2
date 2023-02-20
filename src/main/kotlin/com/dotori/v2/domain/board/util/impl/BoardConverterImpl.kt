package com.dotori.v2.domain.board.util.impl

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.domain.entity.BoardImage
import com.dotori.v2.domain.board.presentation.data.dto.CreateBoardDto
import com.dotori.v2.domain.board.presentation.data.req.CreateBoardReqDto
import com.dotori.v2.domain.board.util.BoardConverter
import com.dotori.v2.domain.member.domain.entity.Member
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class BoardConverterImpl : BoardConverter {

    @Value("\${cloud.aws.s3.url}")
    private val S3_ADDRESS: String? = null

    override fun toDto(createBoardReqDto: CreateBoardReqDto): CreateBoardDto =
        CreateBoardDto(
            title = createBoardReqDto.title,
            content = createBoardReqDto.content
        )


    override fun toEntity(createBoardDto: CreateBoardDto, member: Member): Board =
        Board(
            title = createBoardDto.title,
            content = createBoardDto.content,
            member = member
        )

    override fun toEntity(board: Board, uploadFileUrl: String): BoardImage =
        BoardImage(
            board = board,
            url = S3_ADDRESS + uploadFileUrl
        )
}