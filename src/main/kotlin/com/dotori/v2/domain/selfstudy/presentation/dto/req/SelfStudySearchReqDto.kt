package com.dotori.v2.domain.selfstudy.presentation.dto.req

import org.springframework.web.bind.annotation.RequestParam

data class SelfStudySearchReqDto(
    @RequestParam(value = "name", required = false) val name: String?,
    @RequestParam(value = "grade", required = false) val grade: String?,
    @RequestParam(value = "classNum", required = false) val classNum: String?,
    @RequestParam(value = "gender", required = false) val gender: String?,
)