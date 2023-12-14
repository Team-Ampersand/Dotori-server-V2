package com.dotori.v2.domain.auth.presentation.data.req

import javax.validation.constraints.NotBlank

data class SignInGAuthReqDto(
    @field:NotBlank
    val code: String
)