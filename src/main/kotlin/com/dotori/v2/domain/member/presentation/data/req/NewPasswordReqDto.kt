package com.dotori.v2.domain.member.presentation.data.req

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class NewPasswordReqDto(
    @field:NotBlank
    @field:Size(min = 4)
    val currentPassword: String,
    @field:NotBlank
    @field:Size(min = 4)
    val newPassword: String
)
