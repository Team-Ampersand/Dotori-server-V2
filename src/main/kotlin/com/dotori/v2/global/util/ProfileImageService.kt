package com.dotori.v2.global.util

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.exception.NotAcceptImgExtensionException
import com.dotori.v2.global.config.redis.service.RedisCacheService
import com.dotori.v2.global.thirdparty.aws.s3.S3Service
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ProfileImageService (
    private val s3Service: S3Service,
    private val redisCacheService: RedisCacheService
) {
    fun imageUpload(member: Member, multipartFiles: MultipartFile?, isUpdate: Boolean = false) {

        validateExtension(multipartFiles)

        val uploadFileUrl: String? = s3Service.uploadSingleFile(multipartFiles)

        if (isUpdate) s3Service.deleteFile(member.profileImage!!)

        member.updateProfileImage(uploadFileUrl)

        redisCacheService.updateCacheFromProfile(member.id, uploadFileUrl)
    }

    private fun validateExtension(multipartFiles: MultipartFile?) {
        val acceptList = listOf("jpg", "jpeg", "png")
        val splitFile = multipartFiles?.originalFilename.toString().split(".")
        val extension = splitFile.last().lowercase()

        if (acceptList.none { it == extension })
            throw NotAcceptImgExtensionException()
    }
}