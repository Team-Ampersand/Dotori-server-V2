package com.dotori.v2.global.config

import com.dotori.v2.domain.board.presentation.data.res.ListBoardResDto
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer

@Configuration
@EnableCaching
class RedisCacheConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper().apply {
            registerModule(JavaTimeModule())
            registerModule(KotlinModule())
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        }
    }

    @Bean
    fun contentCacheManager(connectionFactory: RedisConnectionFactory, objectMapper: ObjectMapper): CacheManager {
        val serializer = Jackson2JsonRedisSerializer(ListBoardResDto::class.java).apply {
            setObjectMapper(objectMapper)
        }

        val redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
            .entryTtl(Duration.ofMinutes(10L))

        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(connectionFactory)
            .cacheDefaults(redisCacheConfiguration)
            .build()
    }
    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        return RedisTemplate<String, Any>().apply {
            setConnectionFactory(redisConnectionFactory)
            keySerializer = StringRedisSerializer()
            valueSerializer = GenericJackson2JsonRedisSerializer()
        }
    }
}