package com.dotori.v2.domain.member.domain.repository

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.self_study.presentation.dto.req.SelfStudySearchReqDto
import com.dotori.v2.domain.stu_info.presentation.data.req.SearchRequestDto

interface CustomMemberRepository {
    fun search(searchRequestDto: SearchRequestDto): List<Member>
    fun searchSelfStudyMember(selfStudySearchReqDto: SelfStudySearchReqDto): List<Member>
    fun existMemberByEmail(email: String): Boolean
}