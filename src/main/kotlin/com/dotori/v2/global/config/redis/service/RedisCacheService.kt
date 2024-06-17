package com.dotori.v2.global.config.redis.service

import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.student.presentation.data.res.FindAllStudentResDto
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisCacheService(
    private val redisTemplate: RedisTemplate<String,Any>
) {

    fun getFromCache(key: String): Any? {
        return redisTemplate.opsForValue().get(key)
    }

    fun putToCache(key: String,value: Any) {
        redisTemplate.opsForValue().set(key,value)
    }

    fun updateCacheFromProfile(memberId: Long, uploadFile: String?) {
        val cacheKey = "memberList"
        val cachedData = getFromCache(cacheKey) as? List<FindAllStudentResDto>

        if (cachedData != null) {
            val updatedList = cachedData.map {
                if (it.id == memberId) {
                    FindAllStudentResDto(
                        id = it.id,
                        gender = it.gender,
                        memberName = it.memberName,
                        stuNum = it.stuNum,
                        role = it.role,
                        selfStudyStatus = it.selfStudyStatus,
                        profileImage = uploadFile
                    )
                } else {
                    it
                }
            }
            putToCache(cacheKey,updatedList)
        }
    }

    fun updateCacheFromSelfStudy(memberId: Long,selfStudyStatus: SelfStudyStatus) {
        val cacheKey = "memberList"
        val cachedData = getFromCache(cacheKey) as? List<FindAllStudentResDto>

        if (cachedData != null) {
            val updatedList = cachedData.map {
                if (it.id == memberId) {
                    FindAllStudentResDto(
                        id = it.id,
                        gender = it.gender,
                        memberName = it.memberName,
                        stuNum = it.stuNum,
                        role = it.role,
                        selfStudyStatus = selfStudyStatus,
                        profileImage = it.profileImage
                    )
                } else {
                    it
                }
            }
            putToCache(cacheKey, updatedList)
        }
    }
}
