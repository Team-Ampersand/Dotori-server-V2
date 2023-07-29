package com.dotori.v2.domain.board.service.impl

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.domain.entity.BoardImage
import com.dotori.v2.domain.board.domain.repository.BoardImageRepository
import com.dotori.v2.domain.board.domain.repository.BoardRepository
import com.dotori.v2.domain.board.exception.BoardNotExistsException
import com.dotori.v2.domain.board.presentation.data.req.DeleteMultipleBoardReqDto
import com.dotori.v2.domain.board.service.DeleteMultipleBoardService
import com.dotori.v2.global.thirdparty.aws.s3.S3Service
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DeleteMultipleBoardServiceImpl(
    private val boardRepository: BoardRepository,
    private val boardImageRepository: BoardImageRepository,
    private val s3Service: S3Service
) : DeleteMultipleBoardService {
    override fun execute(deleteMultipleBoardReqDto: DeleteMultipleBoardReqDto) {
        deleteMultipleBoardReqDto.boardIdList
            .forEach {
                val boardInfo: Board = boardRepository.findByIdOrNull(it)
                    ?: throw BoardNotExistsException()

                val boardImages: List<BoardImage> = boardImageRepository.findAllByBoard_Id(it)

                if (boardImages.isEmpty()) {
                    boardRepository.delete(boardInfo)
                } else {
                    for (boardImage in boardImages) {
                        s3Service.deleteFile(boardImage.url.substring(54))
                        boardImageRepository.delete(boardImage)
                    }
                    boardRepository.delete(boardInfo)
                }
            }
    }
}