package com.dotori.v2.global.security.jwt.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "security.jwt.time")
class JwtTimeProperties (
    val accessTime: Long,
    val refreshTime: Long
)