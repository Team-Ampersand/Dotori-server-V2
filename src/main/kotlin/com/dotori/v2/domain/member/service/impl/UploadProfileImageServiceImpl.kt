package com.dotori.v2.domain.member.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.service.UploadProfileImageService
import com.dotori.v2.global.thirdparty.aws.s3.S3Service
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional(rollbackFor = [Exception::class])
class UploadProfileImageServiceImpl(
    private val memberRepository: MemberRepository,
    private val userUtil: UserUtil,
    private val s3Service: S3Service,
) : UploadProfileImageService {
    override fun execute(multipartFiles: List<MultipartFile>) {
        val member: Member = userUtil.fetchCurrentUser()
        val uploadFile: List<String> = s3Service.uploadFile(multipartFiles)

        uploadFile.map {
            member.updateProfileImage(it)
                .let { memberRepository.save(it) }
        }
    }
}