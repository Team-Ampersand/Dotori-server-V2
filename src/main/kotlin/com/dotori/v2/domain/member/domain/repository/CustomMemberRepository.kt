package com.dotori.v2.domain.member.domain.repository

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.projection.SearchMemberProjection
import com.dotori.v2.domain.member.domain.repository.projection.SearchSelfStudyProjection
import com.dotori.v2.domain.selfstudy.presentation.dto.req.SelfStudySearchReqDto
import com.dotori.v2.domain.student.presentation.data.req.SearchRequestDto

interface CustomMemberRepository {
    fun search(searchRequestDto: SearchRequestDto): List<SearchMemberProjection>
    fun searchSelfStudyMember(selfStudySearchReqDto: SelfStudySearchReqDto): List<SearchSelfStudyProjection>
    fun existMemberByEmail(email: String): Boolean
}