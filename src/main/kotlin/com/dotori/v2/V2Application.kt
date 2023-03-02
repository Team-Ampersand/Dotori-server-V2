package com.dotori.v2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class V2Application

fun main(args: Array<String>) {
	runApplication<V2Application>(*args)
}
