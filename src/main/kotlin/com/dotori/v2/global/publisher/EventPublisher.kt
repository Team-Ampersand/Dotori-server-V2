package com.dotori.v2.global.publisher

import io.awspring.cloud.sqs.operations.SendResult

interface EventPublisher {
    fun publishEvent(event: Any, eventType: String): SendResult<String>
}