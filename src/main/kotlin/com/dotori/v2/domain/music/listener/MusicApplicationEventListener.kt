package com.dotori.v2.domain.music.listener

import com.dotori.v2.global.publisher.EventPublisher
import com.dotori.v2.global.squirrel.MusicDotoriEvent
import org.slf4j.LoggerFactory
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import java.io.IOException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeoutException

@Component
class MusicApplicationEventListener(
    private val eventPublisher: EventPublisher
) {

    private val log = LoggerFactory.getLogger(this::class.java.simpleName)

    @Async("squirrelTaskExecutor")
    @Retryable(
        value = [IOException::class, TimeoutException::class, Exception::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 2000, multiplier = 2.0)
    )
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    fun onEvent(event: MusicDotoriEvent) {
        log.info("published music event: {} eventType: MUSIC", event.id)
        CompletableFuture.runAsync {
            eventPublisher.publishEvent(event, "MUSIC")
        }
    }

}
