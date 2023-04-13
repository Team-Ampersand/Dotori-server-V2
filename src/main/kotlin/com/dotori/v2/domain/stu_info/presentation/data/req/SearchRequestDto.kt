package com.dotori.v2.domain.stu_info.presentation.data.req

import com.dotori.v2.domain.member.enums.SelfStudyStatus
import org.springframework.web.bind.annotation.RequestParam

data class SearchRequestDto(
    @RequestParam(value = "name", required = false) val name: String?,
    @RequestParam(value = "grade", required = false) val grade: String?,
    @RequestParam(value = "classNum", required = false) val classNum: String?,
    @RequestParam(value = "gender", required = false) val gender: String?,
    @RequestParam(value = "role", required = false) val role: String?,
    @RequestParam(value = "selfStudy", required = false) val selfStudyStatus: SelfStudyStatus?,
)