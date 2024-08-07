package com.dotori.v2.domain.board.service.impl

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.domain.entity.BoardImage
import com.dotori.v2.domain.board.domain.repository.BoardImageRepository
import com.dotori.v2.domain.board.domain.repository.BoardRepository
import com.dotori.v2.domain.board.exception.BoardNotExistsException
import com.dotori.v2.domain.board.presentation.data.res.ListBoardResDto
import com.dotori.v2.domain.board.service.DeleteBoardService
import com.dotori.v2.global.config.redis.service.RedisCacheService
import com.dotori.v2.global.thirdparty.aws.s3.S3Service
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class DeleteBoardServiceImpl(
    private val boardRepository: BoardRepository,
    private val boardImageRepository: BoardImageRepository,
    private val s3Service: S3Service,
    private val redisCacheService: RedisCacheService
) : DeleteBoardService {

    val CACHE_KEY = "boardList"

    override fun execute(boardId: Long) {
        val boardInfo: Board = boardRepository.findByIdOrNull(boardId)
            ?: throw BoardNotExistsException()

        val boardImages: List<BoardImage> = boardImageRepository.findAllByBoard_Id(boardId)

        if (boardImages.isEmpty()) {
            boardRepository.delete(boardInfo)
        } else {
            for (boardImage in boardImages) {
                s3Service.deleteFile(boardImage.url.substring(54))
                boardImageRepository.delete(boardImage)
            }
            boardRepository.delete(boardInfo)
        }

        evictBoardFromCache(boardId)
    }

    private fun evictBoardFromCache(boardId: Long) {
        val cachedData = redisCacheService.getFromCache(CACHE_KEY) as? ListBoardResDto

        if (cachedData != null) {
            val updatedList = cachedData.boardList.filterNot { it.id == boardId }
            redisCacheService.putToCache(CACHE_KEY, ListBoardResDto(updatedList))
        }
    }


}