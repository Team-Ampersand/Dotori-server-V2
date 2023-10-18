package com.dotori.v2.domain.member.service.impl

import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.service.UpdateProfileImageService
import com.dotori.v2.global.thirdparty.aws.s3.S3Service
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional(rollbackFor = [Exception::class])
class UpdateProfileImageServiceImpl(
    private val memberRepository: MemberRepository,
    private val userUtil: UserUtil,
    private val s3Service: S3Service,
): UpdateProfileImageService {
    override fun execute(multipartFiles: MultipartFile?) {
        val member = userUtil.fetchCurrentUser()
        var uploadFile: String? = s3Service.uploadSingleFile(multipartFiles)
        uploadFile = "https://dotori-s3.s3.ap-northeast-2.amazonaws.com/img/$uploadFile"
        s3Service.deleteFile(member.profileImage!!)
        member.updateProfileImage(uploadFile)
            .let { memberRepository.save(it) }

    }
}