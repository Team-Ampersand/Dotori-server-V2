package com.dotori.v2.domain.member.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.service.ProfileImageService
import com.dotori.v2.domain.member.service.UpdateProfileImageService
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional(rollbackFor = [Exception::class])
class UpdateProfileImageServiceImpl(
    private val userUtil: UserUtil,
    private val profileImageService: ProfileImageService
): UpdateProfileImageService {
    override fun execute(multipartFiles: MultipartFile?) {
        val member: Member = userUtil.fetchCurrentUser()
        profileImageService.imageUpload(member, multipartFiles, isUpdate = true)
    }
}