package com.dotori.v2.domain.board.service

import org.springframework.web.multipart.MultipartFile

interface S3Service {
    fun uploadFile(multipartFiles: List<MultipartFile>?): List<String>
    fun deleteFile(fileName: String)
}