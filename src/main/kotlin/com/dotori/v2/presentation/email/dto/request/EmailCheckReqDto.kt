package com.dotori.v2.presentation.email.dto.request

import javax.validation.constraints.Size

class EmailCheckReqDto(
    @field:Size(min = 6, max = 6)
    val key: String
)