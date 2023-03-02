package com.dotori.v2.domain.music.presentation.data.req

import javax.validation.constraints.NotBlank

data class ApplyMusicReqDto(
    @NotBlank
    val url: String
)
