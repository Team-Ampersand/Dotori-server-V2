package com.dotori.v2.domain.student.service.impl

import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.student.presentation.data.res.FindAllStudentListResDto
import com.dotori.v2.domain.student.presentation.data.res.FindAllStudentResDto
import com.dotori.v2.domain.student.service.FindAllMemberService
import com.dotori.v2.global.config.redis.service.RedisCacheService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class FindAllMemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val redisCacheService: RedisCacheService
) : FindAllMemberService {

    val CACHE_KEY = "memberList"

    override fun execute(): FindAllStudentListResDto {

        val cachedData = redisCacheService.getFromCache(CACHE_KEY)

        if (cachedData != null) {
            return cachedData as FindAllStudentListResDto
        }

        val members = memberRepository.findAll(Sort.by(Sort.Direction.ASC, "stuNum")).map {
            FindAllStudentResDto.of(it)
        }

        val response = FindAllStudentListResDto(members)

        redisCacheService.putToCache(CACHE_KEY, response)

        return response
    }
}