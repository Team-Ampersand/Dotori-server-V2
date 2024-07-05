package com.dotori.v2.global.config.redis.service

import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.student.presentation.data.res.FindAllStudentResDto
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class RedisCacheService(
    private val redisTemplate: RedisTemplate<String, Any>
) {

    fun getFromCache(key: String): Any? {
        return redisTemplate.opsForValue().get(key)
    }

    fun putToCache(key: String, value: Any) {
        redisTemplate.opsForValue().set(key, value)
    }

    fun updateCacheFromProfile(memberId: Long, uploadFile: String?) {
        updateMemberCache(memberId) { it.copy(profileImage = uploadFile) }
    }

    fun updateCacheFromSelfStudy(memberId: Long, selfStudyStatus: SelfStudyStatus) {
        updateMemberCache(memberId) { it.copy(selfStudyStatus = selfStudyStatus) }
    }

    fun initCache(pattern: String) {
        val keys = redisTemplate.keys(pattern)
        if(keys.isNotEmpty()) {
            redisTemplate.delete(pattern)
        }
    }

    private fun updateMemberCache(memberId: Long,update: (FindAllStudentResDto) -> FindAllStudentResDto) {
        val cacheKey = "memberList"
        val cachedData = getFromCache(cacheKey) as? List<FindAllStudentResDto>

        if (cachedData != null) {
            val updatedList = cachedData.map {
                if (it.id == memberId) {
                    update(it)
                } else {
                    it
                }
            }
            putToCache(cacheKey, updatedList)
        }
    }
}
