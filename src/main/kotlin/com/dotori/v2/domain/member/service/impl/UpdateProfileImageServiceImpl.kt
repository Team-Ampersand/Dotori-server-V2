package com.dotori.v2.domain.member.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.global.util.ProfileImageService
import com.dotori.v2.domain.member.service.UpdateProfileImageService
import com.dotori.v2.domain.student.presentation.data.res.FindAllStudentListResDto
import com.dotori.v2.global.config.redis.service.RedisCacheService
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional(rollbackFor = [Exception::class])
class UpdateProfileImageServiceImpl(
    private val userUtil: UserUtil,
    private val profileImageService: ProfileImageService,
    private val redisCacheService: RedisCacheService
): UpdateProfileImageService {

    private val CACHE_KEY = "memberList"

    override fun execute(multipartFiles: MultipartFile?) {
        val member: Member = userUtil.fetchCurrentUser()
        profileImageService.imageUpload(member = member, multipartFiles = multipartFiles)
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