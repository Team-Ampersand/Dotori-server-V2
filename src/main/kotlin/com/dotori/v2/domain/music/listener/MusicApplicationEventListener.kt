package com.dotori.v2.domain.music.listener

import com.dotori.v2.global.publisher.EventPublisher
import com.dotori.v2.global.squirrel.MusicDotoriEvent
import io.awspring.cloud.sqs.operations.SendResult
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class MusicApplicationEventListener(
    private val eventPublisher: EventPublisher
) {

    @Async("squirrelTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    fun onEvent(event: MusicDotoriEvent): SendResult<String> {
        return eventPublisher.publishEvent(event, event.eventType.toString())
    }

}
