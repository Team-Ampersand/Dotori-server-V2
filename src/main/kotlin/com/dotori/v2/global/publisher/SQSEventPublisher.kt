package com.dotori.v2.global.publisher

import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.sqs.operations.SendResult
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.springframework.stereotype.Component

@Component
class SQSEventPublisher(
    private val objectMapper: ObjectMapper,
    private val sqsTemplate: SqsTemplate
) : EventPublisher {

    override fun publishEvent(event: Any, eventType: String): SendResult<String> {
        return sqsTemplate.send { sendOpsTo ->
            sendOpsTo
                .queue("squirrel-sqs")
                .header("eventType", eventType)
                .payload(objectMapper.writeValueAsString(event))
        }
    }

}
