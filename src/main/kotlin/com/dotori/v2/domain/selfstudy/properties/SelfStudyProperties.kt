package com.dotori.v2.domain.selfstudy.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "self-study")
class SelfStudyProperties {
    var allowedTime: String? = null
}