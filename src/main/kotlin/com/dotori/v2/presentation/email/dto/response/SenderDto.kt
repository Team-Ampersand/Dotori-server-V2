package com.dotori.v2.presentation.email.dto.response

import com.amazonaws.services.simpleemail.model.*


class SenderDto(
    val from: String,
    val to: String,
    val subject: String,
    val content: String
) {
    fun toSendRequestDto(): SendEmailRequest {
        val destination: Destination = Destination()
            .withToAddresses(to)
        val message: Message = Message()
            .withSubject(createContent(subject))
            .withBody(Body().withHtml(createContent(content)))
        return SendEmailRequest()
            .withSource(from)
            .withDestination(destination)
            .withMessage(message)
    }

    private fun createContent(text: String): Content {
        return Content()
            .withCharset("UTF-8")
            .withData(text)
    }
}