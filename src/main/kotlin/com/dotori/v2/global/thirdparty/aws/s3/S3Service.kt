package com.dotori.v2.global.thirdparty.aws.s3

import org.springframework.web.multipart.MultipartFile

interface S3Service {
    fun uploadFile(multipartFiles: List<MultipartFile>?): List<String>
    fun deleteFile(fileName: String)
}