package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.exception.NotAcceptImgExtensionException
import com.dotori.v2.global.config.redis.service.RedisCacheService
import com.dotori.v2.global.thirdparty.aws.s3.S3Service
import org.springframework.web.multipart.MultipartFile

abstract class ProfileImageService {
    fun imageUpload(member: Member,
                    multipartFiles: MultipartFile?,
                    s3Service: S3Service,
                    redisCacheService: RedisCacheService,
                    isUpdate: Boolean) {

        validationExtension(multipartFiles)

        val uploadFile: String? = s3Service.uploadSingleFile(multipartFiles)

        if (isUpdate) s3Service.deleteFile(member.profileImage!!)

        member.updateProfileImage(uploadFile)

        redisCacheService.updateCacheFromProfile(member.id, uploadFile)
    }

    private fun validationExtension(multipartFiles: MultipartFile?) {
        val acceptList = listOf("jpg", "jpeg", "png")
        val splitFile = multipartFiles?.originalFilename.toString().split(".")
        val extension = splitFile.last().lowercase()

        if (acceptList.none { it == extension })
            throw NotAcceptImgExtensionException()
    }
}