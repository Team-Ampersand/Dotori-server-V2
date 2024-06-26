package com.dotori.v2.domain.board.service.impl

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.domain.repository.BoardRepository
import com.dotori.v2.domain.board.exception.BoardNotExistsException
import com.dotori.v2.domain.board.presentation.data.dto.ModifyBoardDto
import com.dotori.v2.domain.board.presentation.data.req.ModifyBoardReqDto
import com.dotori.v2.domain.board.presentation.data.res.BoardResDto
import com.dotori.v2.domain.board.presentation.data.res.ListBoardResDto
import com.dotori.v2.domain.board.service.ModifyBoardService
import com.dotori.v2.global.config.redis.service.RedisCacheService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class ModifyBoardServiceImpl(
    private val boardRepository: BoardRepository,
    private val redisCacheService: RedisCacheService
) : ModifyBoardService {

    val CACHE_KEY = "boardList"

    override fun execute(modifyBoardReqDto: ModifyBoardReqDto, boardId: Long) {
        val boardInfo: Board = boardRepository.findByIdOrNull(boardId)
            ?: throw BoardNotExistsException()

        toDto(modifyBoardReqDto)
            .let { boardInfo.updateBoard(title = it.title, content = it.content) }

        updateBoardCache(boardInfo)

    }
    private fun updateBoardCache(board: Board) {
        val cachedData = redisCacheService.getFromCache(CACHE_KEY) as? ListBoardResDto

        if (cachedData != null) {
            val updatedList = cachedData.boardList.map {
                if (it.id == board.id) {
                  BoardResDto.of(board)
                } else {
                    it
                }
            }.toMutableList()

            updatedList.sortByDescending { it.id }

            redisCacheService.putToCache(CACHE_KEY, ListBoardResDto(updatedList))
        }
    }

    private fun toDto(modifyBoardReqDto: ModifyBoardReqDto): ModifyBoardDto =
        ModifyBoardDto(
            title = modifyBoardReqDto.title,
            content = modifyBoardReqDto.content
        )

}