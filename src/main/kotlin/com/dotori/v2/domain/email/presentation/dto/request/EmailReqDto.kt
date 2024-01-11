package com.dotori.v2.domain.email.presentation.dto.request

import javax.validation.constraints.Pattern

class EmailReqDto(
    @field:Pattern(regexp = "^[a-zA-Z0-9]+@gsm.hs.kr$")
    val email:String,
)