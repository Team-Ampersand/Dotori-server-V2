package com.dotori.v2.domain.member.presentation

import com.dotori.v2.domain.member.service.DeleteProfileImageService
import com.dotori.v2.domain.member.service.UpdateProfileImageService
import com.dotori.v2.domain.member.service.UploadProfileImageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v2/members")
class MemberController(
    private val uploadProfileImageService: UploadProfileImageService,
    private val updateProfileImageService: UpdateProfileImageService,
    private val deleteProfileImageService: DeleteProfileImageService
) {

    @PostMapping("/profileImage")
    fun uploadProfileImage(@RequestParam(value = "image") multipartFiles: MultipartFile?): ResponseEntity<Void> =
        uploadProfileImageService.execute(multipartFiles)
            .run { ResponseEntity.ok().build() }

    @PatchMapping("/profileImage")
    fun updateProfileImage(@RequestParam(value = "image") multipartFiles: MultipartFile?): ResponseEntity<Void> =
        updateProfileImageService.execute(multipartFiles)
            .run { ResponseEntity.ok().build() }

    @DeleteMapping("/profileImage")
    fun deleteProfileImage(): ResponseEntity<Void> =
        deleteProfileImageService.execute()
            .run { ResponseEntity.ok().build() }

}