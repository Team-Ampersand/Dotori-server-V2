package com.dotori.v2.domain.selfstudy.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "self-study")
class SelfStudyProperties(
    allowedStartTime: String,
    allowedEndTime: String
) {
    val allowedStartTime = allowedStartTime
    val allowedEndTime =  allowedEndTime
}