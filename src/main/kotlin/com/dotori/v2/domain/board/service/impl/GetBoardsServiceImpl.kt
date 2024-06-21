package com.dotori.v2.domain.board.service.impl

import com.dotori.v2.domain.board.domain.repository.BoardRepository
import com.dotori.v2.domain.board.presentation.data.res.BoardResDto
import com.dotori.v2.domain.board.presentation.data.res.ListBoardResDto
import com.dotori.v2.domain.board.service.GetBoardsService
import com.dotori.v2.global.config.redis.service.RedisCacheService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class GetBoardsServiceImpl(
    private val boardRepository: BoardRepository,
    private val redisCacheService: RedisCacheService
) : GetBoardsService {

    override fun execute(): ListBoardResDto {
        val cacheKey = "boardList"

        val cachedData = redisCacheService.getFromCache(cacheKey)
        if (cachedData != null) {
            return cachedData as ListBoardResDto
        }

        val boardList = boardRepository.findAllByOrderByCreatedDateDesc()
                    .map { BoardResDto.of(it) }

        val listBoardResDto = ListBoardResDto(boardList)

        redisCacheService.putToCache(cacheKey, listBoardResDto)

        return listBoardResDto

    }

}