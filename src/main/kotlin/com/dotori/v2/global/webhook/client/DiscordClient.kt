package com.dotori.v2.global.webhook.client

interface DiscordClient {
    fun sendMessage(message: String)
}