package com.dotori.v2.domain.board.service.impl

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.domain.entity.BoardImage
import com.dotori.v2.domain.board.presentation.data.dto.CreateBoardDto
import com.dotori.v2.domain.board.presentation.data.req.CreateBoardReqDto
import com.dotori.v2.domain.board.service.CreateBoardService
import com.dotori.v2.domain.board.service.S3Service
import com.dotori.v2.domain.board.util.BoardSaveUtil
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.global.util.UserUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional(rollbackFor = [Exception::class])
class CreateBoardServiceImpl(
    private val userUtil: UserUtil,
    private val s3Service: S3Service,
    private val boardSaveUtil: BoardSaveUtil
) : CreateBoardService {

    @Value("\${cloud.aws.s3.url}")
    private val S3_ADDRESS: String? = null

    override fun execute(createBoardReqDto: CreateBoardReqDto, multipartFiles: List<MultipartFile>?): Board {
        val member: Member = userUtil.fetchCurrentUser()
        val createBoardDto: CreateBoardDto = toDto(createBoardReqDto = createBoardReqDto)
        if (multipartFiles == null) {
            return toEntity(createBoardDto, member)
                .let { boardSaveUtil.saveBoard(board = it) }
        }
        val uploadFile: List<String> = s3Service.uploadFile(multipartFiles)
        val board: Board = toEntity(createBoardDto, member)
            .let { boardSaveUtil.saveBoard(board = it) }
        for (uploadFileUrl: String in uploadFile) {
            toEntity(board = board, uploadFileUrl)
                .let { boardSaveUtil.saveBoardImage(boardImage = it) }
        }
        return board
    }

    private fun toDto(createBoardReqDto: CreateBoardReqDto): CreateBoardDto =
        CreateBoardDto(
            title = createBoardReqDto.title,
            content = createBoardReqDto.content
        )

    private fun toEntity(createBoardDto: CreateBoardDto, member: Member): Board =
        Board(
            content = createBoardDto.content,
            title = createBoardDto.title,
            member = member
        )

    private fun toEntity(board: Board, uploadFileUrl: String): BoardImage =
        BoardImage(
            board = board,
            url = S3_ADDRESS + uploadFileUrl
        )
}