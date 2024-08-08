package com.dotori.v2.global.config

import com.dotori.v2.domain.selfstudy.properties.SelfStudyProperties
import com.dotori.v2.global.config.gauth.properties.GAuthProperties
import com.dotori.v2.global.security.jwt.properties.JwtProperties
import com.dotori.v2.global.security.jwt.properties.JwtTimeProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(
    basePackageClasses = [
        GAuthProperties::class,
        JwtProperties::class,
        JwtTimeProperties::class,
        SelfStudyProperties::class
    ]
)
class ConfigurationPropertiesScanConfig