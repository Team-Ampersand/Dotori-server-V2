package com.dotori.v2.domain.member.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.global.util.ProfileImageService
import com.dotori.v2.domain.member.service.UploadProfileImageService
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional(rollbackFor = [Exception::class])
class UploadProfileImageServiceImpl(
    private val userUtil: UserUtil,
    private val profileImageService: ProfileImageService
): UploadProfileImageService {
    override fun execute(multipartFiles: MultipartFile?) {
        val member: Member = userUtil.fetchCurrentUser()
        profileImageService.imageUpload(member = member, multipartFiles = multipartFiles)
    }
}