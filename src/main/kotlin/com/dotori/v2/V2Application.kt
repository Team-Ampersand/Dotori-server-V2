package com.dotori.v2

import com.dotori.v2.global.webhook.client.DiscordClient
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*
import javax.annotation.PostConstruct
import org.springframework.core.env.Environment
import java.time.LocalDateTime

@SpringBootApplication
class V2Application(
    private val discordClient: DiscordClient,
    private val env: Environment
) {

	val log = LoggerFactory.getLogger(this::class.java.name)!!

	@PostConstruct
	fun timeZone() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
		log.info("Server Started At {}", Date())

        discordClient.sendMessage("""
            서버가 재시작되었습니다. profile: ${env.activeProfiles.first()}
            
            uptime: ${LocalDateTime.now()}
            """.trimIndent())
	}
}

fun main(args: Array<String>) {
	runApplication<V2Application>(*args)
}
