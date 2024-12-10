package com.dotori.v2.global.config.redis.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedisCacheService(
    private val redisTemplate: RedisTemplate<String, Any>
) {

    fun getFromCache(key: String): Any? {
        return redisTemplate.opsForValue().get(key)
    }

    fun putToCache(key: String, value: Any) {
        redisTemplate.opsForValue().set(key, value)

        if (key == "memberList")
            redisTemplate.expire(key, 1, TimeUnit.HOURS)
    }

    fun deleteFromCache(key: String) {
        redisTemplate.delete(key)
    }

    fun putToCacheMusic(date: String, value: Any) {
        val key = "musicList:$date"
        redisTemplate.opsForValue().set(key, value)
        redisTemplate.expire(key, 10, TimeUnit.MINUTES)
    }

    fun getFromCacheMusic(date: String): Any? {
        return redisTemplate.opsForValue().get("musicList:$date")
    }

    fun deleteFromCacheMusic(date: String) {
        redisTemplate.delete("musicList:$date")
    }

    fun initCache(pattern: String) {
        val keys = redisTemplate.keys(pattern)
        if(keys.isNotEmpty()) {
            redisTemplate.delete(pattern)
        }
    }
}