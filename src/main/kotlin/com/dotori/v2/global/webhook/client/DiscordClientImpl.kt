package com.dotori.v2.global.webhook.client

import com.dotori.v2.global.webhook.protocol.DiscordSendMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class DiscordClientImpl(
    private val restTemplate: RestTemplate
) : DiscordClient {

    @Value("\${discord.webhook.url}")
    private lateinit var webhookUrl: String
    override fun sendMessage(message: String) {
        restTemplate.postForEntity(
            webhookUrl, DiscordSendMessage.of(message), Void::class.java
        )
    }
}