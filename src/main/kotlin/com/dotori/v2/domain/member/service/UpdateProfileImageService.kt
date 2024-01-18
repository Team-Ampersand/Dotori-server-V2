package com.dotori.v2.domain.member.service

import org.springframework.web.multipart.MultipartFile

interface UpdateProfileImageService {
    fun execute(multipartFiles: MultipartFile?)
}