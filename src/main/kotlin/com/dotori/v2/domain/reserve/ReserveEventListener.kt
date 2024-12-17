package com.dotori.v2.domain.reserve

import com.dotori.v2.global.publisher.EventPublisher
import com.dotori.v2.global.squirrel.ReserveDotoriEvent
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
class ReserveEventListener(
    private val eventPublisher: EventPublisher
) {

    private val log = LoggerFactory.getLogger(this::class.java.simpleName)

    @Async("squirrelTaskExecutor")
    @Retryable(
        value = [IOException::class, TimeoutException::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 2000, multiplier = 2.0)
    )
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    fun onEvent(event: ReserveDotoriEvent) {
        log.info("published reserve event: {} eventType: {}", event.id, "RESERVE")
        CompletableFuture.runAsync {
            eventPublisher.publishEvent(event, "RESERVE")
        }
    }

}

