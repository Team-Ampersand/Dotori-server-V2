package com.dotori.v2.global.publisher

import com.amazonaws.services.sqs.AmazonSQSAsync
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class SQSEventPublisher(
    private val objectMapper: ObjectMapper,
    private val amazonSQS: AmazonSQSAsync
) : EventPublisher {

    private val queueMessagingTemplate = QueueMessagingTemplate(amazonSQS)
    private val log = LoggerFactory.getLogger(this::class.java.simpleName)

    override fun publishEvent(event: Any, eventType: String) {
        val message = MessageBuilder
            .withPayload(objectMapper.writeValueAsString(event))
            .setHeader("eventType", eventType)
            .build()
        log.info("publish event {} event-type {}", event, eventType)
        queueMessagingTemplate.send("squirrel-sqs", message)
    }
}
