package com.dotori.v2.domain.member.service.impl

import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.service.WithdrawalService
import com.dotori.v2.domain.student.presentation.data.res.FindAllStudentListResDto
import com.dotori.v2.global.config.redis.service.RedisCacheService
import com.dotori.v2.global.thirdparty.aws.s3.S3Service
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class WithdrawalServiceImpl(
    private val memberRepository: MemberRepository,
    private val s3Service: S3Service,
    private val userUtil: UserUtil,
    private val redisCacheService: RedisCacheService
) : WithdrawalService {

    private val CACHE_KEY = "memberList"

    override fun execute() {
        val currentUser = userUtil.fetchCurrentUser()
        currentUser.profileImage?.let { s3Service.deleteFile(it) }
        memberRepository.delete(currentUser)
        initCache()
    }

    private fun initCache() {
        val cachedData =
            redisCacheService.getFromCache(CACHE_KEY) as? FindAllStudentListResDto

        if (cachedData != null) {
            redisCacheService.deleteFromCache(CACHE_KEY)
        }
    }
}