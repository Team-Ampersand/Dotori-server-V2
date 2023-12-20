package com.dotori.v2.domain.selfstudy.service

import com.dotori.v2.domain.selfstudy.presentation.dto.res.SelfStudyMemberListResDto

interface GetSelfStudyByMemberNameService {
    fun execute(memberName: String): SelfStudyMemberListResDto

}