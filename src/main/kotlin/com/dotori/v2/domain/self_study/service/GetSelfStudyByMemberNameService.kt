package com.dotori.v2.domain.self_study.service

import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberListResDto

interface GetSelfStudyByMemberNameService {
    fun execute(memberName: String): SelfStudyMemberListResDto

}