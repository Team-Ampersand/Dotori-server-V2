package com.dotori.v2.global.squirrel

import java.time.LocalDateTime
import java.util.*

data class MusicDotoriEvent(
    val id: String,
    val username: String,
    val createdAt: LocalDateTime,
    val env: EventEnv,
    val activeType: ActiveType,
    val eventType: EventType,
    val musicTitle: String,
) {
    companion object {
        private fun createMusicEvent(
            username: String,
            createdAt: LocalDateTime,
            env: String,
            title: String,
            activeType: ActiveType
        ) = MusicDotoriEvent(
            id = UUID.randomUUID().toString(),
            username = username,
            createdAt = createdAt,
            env = EventEnv.valueOf(env.uppercase(Locale.getDefault())),
            activeType = activeType,
            eventType = EventType.MUSIC,
            musicTitle = title
        )

        fun ofCreateMusicEvent(username: String,createdAt: LocalDateTime,env: String,title: String) =
            createMusicEvent(username,createdAt,env,title,ActiveType.CREATE)

        fun ofDeleteMusicEvent(username: String,createdAt: LocalDateTime,env: String,title: String) =
            createMusicEvent(username,createdAt,env,title,ActiveType.DELETE)
    }
}