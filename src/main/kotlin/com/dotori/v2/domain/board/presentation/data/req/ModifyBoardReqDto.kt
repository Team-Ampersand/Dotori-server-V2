package com.dotori.v2.domain.board.presentation.data.req

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class ModifyBoardReqDto(
    @NotBlank
    @Size(min = 1, max = 40)
    val title: String,
    @NotBlank
    @Size(min = 1, max = 5000)
    val content: String
)