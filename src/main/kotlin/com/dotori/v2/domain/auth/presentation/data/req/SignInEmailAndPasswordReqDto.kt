package com.dotori.v2.domain.auth.presentation.data.req
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class SignInEmailAndPasswordReqDto(
    @field:NotBlank
    @field:Pattern(regexp = "^[a-zA-Z0-9]+@gsm.hs.kr$")
    val email: String,
    @field:NotBlank
    @field:Size(min = 4)
    val password: String,
)
