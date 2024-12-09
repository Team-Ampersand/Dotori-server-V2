package com.dotori.v2.global.squirrel

import java.time.LocalDateTime
import java.util.*

data class ReserveDotoriEvent(
    val id: String,
    val username: String,
    val createdAt: LocalDateTime,
    val env: EventEnv,
    val activeType: ActiveType,
    val eventType: EventType,
) {
    companion object {
        private fun createSelfStudyEvent(
            username: String,
            createdAt: LocalDateTime,
            env: String,
            activeType: ActiveType
        ) = ReserveDotoriEvent(
            id = UUID.randomUUID().toString(),
            username = username,
            createdAt = createdAt,
            env = EventEnv.valueOf(env.uppercase(Locale.getDefault())),
            activeType = activeType,
            eventType = EventType.SELFSTUDY,
        )

        private fun createMassageEvent(
            username: String,
            createdAt: LocalDateTime,
            env: String,
            activeType: ActiveType
        ) = ReserveDotoriEvent(
            id = UUID.randomUUID().toString(),
            username = username,
            createdAt = createdAt,
            env = EventEnv.valueOf(env.uppercase(Locale.getDefault())),
            activeType = activeType,
            eventType = EventType.MASSAGE,
        )

        fun ofCreateSelfStudyEvent(username: String, createdAt: LocalDateTime, env: String) =
            createSelfStudyEvent(username, createdAt, env, ActiveType.CREATE)

        fun ofDeleteSelfStudyEvent(username: String, createdAt: LocalDateTime, env: String) =
            createSelfStudyEvent(username, createdAt, env, ActiveType.DELETE)

        fun ofCreateMassageEvent(username: String, createdAt: LocalDateTime, env: String) =
            createMassageEvent(username, createdAt, env, ActiveType.CREATE)

        fun ofDeleteMassageEvent(username: String, createdAt: LocalDateTime, env: String) =
            createMassageEvent(username, createdAt, env, ActiveType.DELETE)
    }
}
