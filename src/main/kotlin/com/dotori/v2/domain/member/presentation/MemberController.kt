package com.dotori.v2.domain.member.presentation

import com.dotori.v2.domain.member.presentation.data.req.NewPasswordReqDto
import com.dotori.v2.domain.member.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RestController
@RequestMapping("/v2/members")
class MemberController(
    private val logoutService: LogoutService,
    private val withdrawalService: WithdrawalService,
    private val changeAuthPasswordService: ChangeAuthPasswordService,
    private val uploadProfileImageService: UploadProfileImageService,
    private val updateProfileImageService: UpdateProfileImageService,
    private val deleteProfileImageService: DeleteProfileImageService
) {
    @DeleteMapping("/logout")
    fun logout(): ResponseEntity<Void> =
        logoutService.execute()
            .run { ResponseEntity.ok().build() }

    @DeleteMapping("/withdrawal")
    fun withdrawal(): ResponseEntity<Void> =
        withdrawalService.execute()
            .run { ResponseEntity.ok().build() }

    @PatchMapping("/password")
    fun changePassword(@Valid @RequestBody newPasswordReqDto: NewPasswordReqDto): ResponseEntity<Void> =
        changeAuthPasswordService.execute(newPasswordReqDto)
            .run { ResponseEntity.ok().build() }

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