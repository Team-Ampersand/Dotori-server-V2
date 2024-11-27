package com.dotori.v2.global.squirrel

import java.time.LocalDateTime

data class ReserveDotoriEvent(
    val id: String,
    val username: String,
    val createdAt: LocalDateTime,
    val env: EventEnv,
    val activeType: ActiveType,
    val eventType: EventType,
)
