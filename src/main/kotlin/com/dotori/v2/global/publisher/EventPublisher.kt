package com.dotori.v2.global.publisher

interface EventPublisher {
    fun publishEvent(event: Any, eventType: String)
}