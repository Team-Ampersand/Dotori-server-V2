package com.dotori.v2.domain.massage.util

import com.dotori.v2.domain.massage.domain.entity.Massage
import com.dotori.v2.domain.massage.domain.repository.MassageRepository
import com.dotori.v2.domain.member.domain.entity.Member
import org.springframework.stereotype.Component

@Component
class SaveMassageUtil(
    private val massageRepository: MassageRepository,
) {
    fun save(member: Member): Massage =
        massageRepository.save(Massage(member = member))
}