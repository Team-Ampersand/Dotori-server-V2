package com.dotori.v2.domain.student.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import com.dotori.v2.domain.student.presentation.data.req.ModifyStudentInfoRequest
import com.dotori.v2.domain.student.presentation.data.res.FindAllStudentResDto
import com.dotori.v2.domain.student.service.ModifyStudentInfoService
import com.dotori.v2.global.config.redis.service.RedisCacheService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(rollbackFor = [Exception::class])
class ModifyStudentInfoServiceImpl(
    private val memberRepository: MemberRepository,
    private val redisCacheService: RedisCacheService
) : ModifyStudentInfoService {

    private val CACHE_KEY = "memberList"

    override fun execute(modifyStudentInfoRequest: ModifyStudentInfoRequest) {
        val member = memberRepository.findByIdOrNull(modifyStudentInfoRequest.memberId) ?: throw MemberNotFoundException()
        updateMemberInfo(member, modifyStudentInfoRequest)
    }

    private fun updateMemberInfo(member: Member, modifyStudentInfoRequest: ModifyStudentInfoRequest) {
        val newMember = Member(
            id = member.id,
            memberName = modifyStudentInfoRequest.memberName,
            stuNum = modifyStudentInfoRequest.stuNum,
            email = member.email,
            password = member.password,
            gender = modifyStudentInfoRequest.gender,
            roles = Collections.singletonList(modifyStudentInfoRequest.role),
            ruleViolation = member.ruleViolation,
            profileImage = member.profileImage
        )

        val cachedData = redisCacheService.getFromCache(CACHE_KEY) as? List<FindAllStudentResDto>

        if (cachedData != null) {
            redisCacheService.putToCache(CACHE_KEY, cachedData)
        }

        memberRepository.save(newMember)
    }
}