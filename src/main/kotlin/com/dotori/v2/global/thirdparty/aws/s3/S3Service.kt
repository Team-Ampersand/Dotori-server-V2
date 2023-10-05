package com.dotori.v2.global.thirdparty.aws.s3

import org.springframework.web.multipart.MultipartFile

interface S3Service {
    fun uploadListFile(multipartFiles: List<MultipartFile>?): List<String>
    fun uploadSingleFile(multipartFiles: MultipartFile?): String?
    fun deleteFile(fileName: String)
}