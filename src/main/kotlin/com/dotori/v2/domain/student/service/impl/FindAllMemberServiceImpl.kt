package com.dotori.v2.domain.student.service.impl

import com.dotori.v2.domain.member.domain.repository.MemberRepository
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
    override fun execute(): List<FindAllStudentResDto> {
        val cacheKey = "memberList"

        val cachedData = redisCacheService.getFromCache(cacheKey)
        if (cachedData != null) {
            return cachedData as List<FindAllStudentResDto>
        }

        val members = memberRepository.findAll(Sort.by(Sort.Direction.ASC, "stuNum")).map {
            FindAllStudentResDto(
                id = it.id,
                gender = it.gender,
                memberName = it.memberName,
                stuNum = it.stuNum,
                role = it.roles[0],
                selfStudyStatus = it.selfStudyStatus,
                profileImage = it.profileImage
            )
        }

        redisCacheService.putToCache(cacheKey, members)

        return members
    }
}
