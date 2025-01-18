package com.dotori.v2.global.webhook.protocol

data class DiscordSendMessage(
    val content: String
) {
    companion object {
        fun of(content: String): DiscordSendMessage {
            return DiscordSendMessage(content)
        }
    }
}
