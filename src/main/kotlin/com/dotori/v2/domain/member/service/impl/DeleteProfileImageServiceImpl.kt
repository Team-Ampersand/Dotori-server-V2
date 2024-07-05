package com.dotori.v2.domain.member.service.impl

import com.dotori.v2.domain.member.service.DeleteProfileImageService
import com.dotori.v2.global.thirdparty.aws.s3.S3Service
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class DeleteProfileImageServiceImpl(
    private val userUtil: UserUtil,
    private val s3Service: S3Service
) : DeleteProfileImageService {

    override fun execute() {
        val member = userUtil.fetchCurrentUser()
        s3Service.deleteFile(member.profileImage!!)
        member.updateProfileImage(null)
    }

}