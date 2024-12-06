package com.dotori.v2.global.config.config

import com.dotori.v2.global.security.jwt.properties.JwtProperties
import com.dotori.v2.global.security.jwt.properties.JwtTimeProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(
    basePackageClasses = [
        JwtProperties::class,
        JwtTimeProperties::class
    ]
)
class ConfigurationPropertiesScanConfig