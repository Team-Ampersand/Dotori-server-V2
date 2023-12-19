package com.dotori.v2.domain.selfstudy.util

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.selfstudy.domain.entity.SelfStudy
import com.dotori.v2.domain.selfstudy.domain.repository.SelfStudyRepository
import org.springframework.stereotype.Component

@Component
class SaveSelfStudyUtil(
    private val selfStudyRepository: SelfStudyRepository
) {
    fun save(member: Member){
        selfStudyRepository.save(SelfStudy(member = member))
    }
}