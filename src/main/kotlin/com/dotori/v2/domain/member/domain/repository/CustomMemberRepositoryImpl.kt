package com.dotori.v2.domain.member.domain.repository

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.stu_info.presentation.data.req.SearchRequestDto

class CustomMemberRepositoryImpl : CustomMemberRepository {


    override fun search(searchRequestDto: SearchRequestDto): List<Member> {
        TODO("Not yet implemented")
    }
}