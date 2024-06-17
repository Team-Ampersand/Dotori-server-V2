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
    private val redisCacheService: RedisCacheService,
) : ModifyStudentInfoService {

    override fun execute(modifyStudentInfoRequest: ModifyStudentInfoRequest) {
        val member = memberRepository.findByIdOrNull(modifyStudentInfoRequest.memberId)
            ?: throw MemberNotFoundException()

        updateMemberInfo(member, modifyStudentInfoRequest)

        updateCache(modifyStudentInfoRequest)
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
        memberRepository.save(newMember)
    }

    private fun updateCache(modifyStudentInfoRequest: ModifyStudentInfoRequest) {
        val cacheKey = "memberList"
        val cachedData = redisCacheService.getFromCache(cacheKey) as? List<FindAllStudentResDto>?

        if (cachedData != null) {
            val updatedList = cachedData.map {
                if (it.id == modifyStudentInfoRequest.memberId) {
                    FindAllStudentResDto(
                        id = it.id,
                        gender = modifyStudentInfoRequest.gender,
                        memberName = modifyStudentInfoRequest.memberName,
                        stuNum = modifyStudentInfoRequest.stuNum,
                        role = modifyStudentInfoRequest.role,
                        selfStudyStatus = it.selfStudyStatus,
                        profileImage = it.profileImage
                    )
                } else {
                    it
                }
            }
            redisCacheService.putToCache(cacheKey, updatedList)
        }
    }
}
