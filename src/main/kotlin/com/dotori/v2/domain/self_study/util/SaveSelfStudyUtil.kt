package com.dotori.v2.domain.self_study.util

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.self_study.domain.entity.SelfStudy
import com.dotori.v2.domain.self_study.domain.repository.SelfStudyRepository
import org.springframework.stereotype.Component

@Component
class SaveSelfStudyUtil(
    private val selfStudyRepository: SelfStudyRepository
) {
    fun save(member: Member){
        selfStudyRepository.save(SelfStudy(member = member))
    }
}