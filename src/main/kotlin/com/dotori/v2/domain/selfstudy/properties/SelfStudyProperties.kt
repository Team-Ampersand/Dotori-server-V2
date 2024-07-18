package com.dotori.v2.domain.selfstudy.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "self-study")
class SelfStudyProperties {
    var allowedStartTime: String = "20:00"
    var allowedEndTime: String = "20:59"
}