package com.dotori.v2.domain.rule.presentation.data.req

import org.springframework.web.bind.annotation.RequestParam

data class StudentListReqDto(
    @RequestParam(value = "name", required = false) val name: String?,
    @RequestParam(value = "grade", required = false) val grade: String?,
    @RequestParam(value = "classNum", required = false) val classNum: String?,
    @RequestParam(value = "gender", required = false) val gender: String?,
)
