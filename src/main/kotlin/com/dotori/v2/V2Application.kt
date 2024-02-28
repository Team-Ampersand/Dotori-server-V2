package com.dotori.v2

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*
import javax.annotation.PostConstruct
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing

@SpringBootApplication
// @EnableBatchProcessing
class V2Application{
	val log = LoggerFactory.getLogger(this::class.java.name)!!
	@PostConstruct
	fun timeZone() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
		log.info("Server Started At {}", Date())
	}
}

fun main(args: Array<String>) {
	runApplication<V2Application>(*args)
}
