package com.dotori.v2.domain.reserve

import com.dotori.v2.global.publisher.EventPublisher
import com.dotori.v2.global.squirrel.ReserveDotoriEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ReserveEventListener(
    private val eventPublisher: EventPublisher
) {

    @Async("squirrelTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    fun onEvent(event: ReserveDotoriEvent) {
        eventPublisher.publishEvent(event, event.eventType.toString())
    }
}
