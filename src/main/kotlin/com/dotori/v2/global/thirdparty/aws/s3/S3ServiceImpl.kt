package com.dotori.v2.global.thirdparty.aws.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.io.InputStream
import java.util.*

@Service
class S3ServiceImpl(
    private val amazonS3: AmazonS3
) : S3Service {

    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String? = null

    override fun uploadListFile(multipartFiles: List<MultipartFile>?): List<String> {
        val fileNameList = ArrayList<String>()

        multipartFiles.orEmpty().forEach { file ->
            val fileName: String = createFileName(file.originalFilename.toString())
            val objectMetadata = ObjectMetadata()
            objectMetadata.contentLength = file.size
            objectMetadata.contentType = file.contentType
            try {
                val inputStream: InputStream = file.inputStream
                amazonS3.putObject(
                    PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
                )
            } catch (e: IOException) {
                throw IllegalStateException("파일 업로드에 실패했습니다.")
            }
            fileNameList.add(fileName)
        }
        return fileNameList
    }

    override fun uploadSingleFile(multipartFile: MultipartFile?): String? {
        if (multipartFile == null || multipartFile.isEmpty) {
            return null
        }

        val fileName = createFileName(multipartFile.originalFilename.orEmpty())
        val objectMetadata = ObjectMetadata().apply {
            contentLength = multipartFile.size
            contentType = multipartFile.contentType
        }

        try {
            amazonS3.putObject(
                PutObjectRequest(bucket, fileName, multipartFile.inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead)
            )
            return fileName
        } catch (e: IOException) {
            throw IllegalStateException("파일 업로드에 실패했습니다.")
        }
    }

    override fun deleteFile(fileName: String) {
        amazonS3.deleteObject(DeleteObjectRequest(bucket, fileName))
    }

    private fun createFileName(fileName: String) =
        UUID.randomUUID().toString() + fileName
}