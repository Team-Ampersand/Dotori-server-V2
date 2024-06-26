package com.dotori.v2.global.config.redis.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "cache-key")
class CacheKeyProperties (
    val boardKey: String
)