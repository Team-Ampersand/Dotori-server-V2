package com.dotori.v2.domain.selfstudy.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import com.dotori.v2.domain.selfstudy.service.BanSelfStudyService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(rollbackFor = [Exception::class])
class BanSelfStudyServiceImpl(
    private val memberRepository: MemberRepository
) : BanSelfStudyService {
    @CacheEvict(cacheNames = ["memberList"], key = "'memberList'", cacheManager = "contentCacheManager")
    override fun execute(id: Long) {
        val member = memberRepository.findByIdOrNull(id) ?: throw MemberNotFoundException()
        updateSelfStudyAndExpiredDate(member, SelfStudyStatus.IMPOSSIBLE, LocalDateTime.now().plusDays(7))
    }

    private fun updateSelfStudyAndExpiredDate(
        findMember: Member,
        selfStudyStatus: SelfStudyStatus,
        localDateTime: LocalDateTime?
    ) {
        findMember.updateSelfStudyStatus(selfStudyStatus)
        findMember.updateSelfStudyExpiredDate(localDateTime)
    }
}