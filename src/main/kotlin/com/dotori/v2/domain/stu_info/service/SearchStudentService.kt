package com.dotori.v2.domain.stu_info.service

import com.dotori.v2.domain.stu_info.presentation.data.req.SearchRequestDto
import com.dotori.v2.domain.stu_info.presentation.data.res.SearchStudentListResDto

interface SearchStudentService {
    fun execute(searchRequestDto: SearchRequestDto): List<SearchStudentListResDto>
}