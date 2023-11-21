package com.dotori.v2.domain.member.presentation.data.req

import javax.validation.constraints.NotBlank

data class SignInReqDto(
    @field:NotBlank
    val code: String
)