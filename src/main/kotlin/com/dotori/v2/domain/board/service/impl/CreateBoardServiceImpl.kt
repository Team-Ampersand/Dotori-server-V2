package com.dotori.v2.domain.board.service.impl

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.domain.repository.BoardImageRepository
import com.dotori.v2.domain.board.domain.repository.BoardRepository
import com.dotori.v2.domain.board.presentation.data.dto.CreateBoardDto
import com.dotori.v2.domain.board.service.CreateBoardService
import com.dotori.v2.domain.board.service.S3Service
import com.dotori.v2.domain.board.util.BoardConverter
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional(rollbackFor = [Exception::class])
class CreateBoardServiceImpl(
    private val userUtil: UserUtil,
    private val boardRepository: BoardRepository,
    private val boardConverter: BoardConverter,
    private val s3Service: S3Service,
    private val boardImageRepository: BoardImageRepository
) : CreateBoardService {

    override fun execute(createBoardDto: CreateBoardDto, multipartFiles: List<MultipartFile>?): Board {
        val member: Member = userUtil.fetchCurrentUser()
        if (multipartFiles == null) {
            return boardConverter.toEntity(createBoardDto, member)
                .let { boardRepository.save(it) }
        }
        val uploadFile: List<String> = s3Service.uploadFile(multipartFiles)
        val board: Board = boardConverter.toEntity(createBoardDto, member)
            .let { boardRepository.save(it) }
        for (uploadFileUrl: String in uploadFile) {
            boardConverter.toEntity(board = board, uploadFileUrl)
                .let { boardImageRepository.save(it) }
        }
        return board
    }
}