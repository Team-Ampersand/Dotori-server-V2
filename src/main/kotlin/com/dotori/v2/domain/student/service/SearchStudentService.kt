package com.dotori.v2.domain.student.service

import com.dotori.v2.domain.member.domain.repository.projection.SearchMemberProjection
import com.dotori.v2.domain.student.presentation.data.req.SearchRequestDto
import com.dotori.v2.domain.student.presentation.data.res.SearchStudentListResDto

interface SearchStudentService {
    fun execute(searchRequestDto: SearchRequestDto): List<SearchStudentListResDto>
}