package com.dotori.v2.domain.member.service

import org.springframework.web.multipart.MultipartFile

interface UploadProfileImageService {
    fun execute(multipartFiles: List<MultipartFile>)
}