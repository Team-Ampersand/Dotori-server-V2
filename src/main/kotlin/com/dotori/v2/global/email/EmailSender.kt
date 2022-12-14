package com.dotori.v2.global.email

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder
import com.amazonaws.services.simpleemail.model.SendEmailResult
import com.dotori.v2.domain.email.presentation.dto.response.SenderDto
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class EmailSender(
    private val amazonSimpleEmailService: AmazonSimpleEmailService
){

    @Value("\${aws.ses.verified.email}")
    private val from = ""
    private val log = LoggerFactory.getLogger(this::class.simpleName)


    fun send(receivers: String?, key: String) {
        val subject = "π[DOTORI] μΈμ¦ ν€"
        var message = "<p style=\"color:blueviolet\">μλνμΈμ Dotori κ³μ μ μ¬μ©ν  μΌνμ© μ½λμ λν μμ²­μ λ°μμ΅λλ€.</p>"
        message += "<p>μΌνμ© μ½λ: $key</p>"
        message += "<p>μ΄ μ½λλ₯Ό μμ²­νμ§ μμ κ²½μ° μ΄ λ©μΌμ λ¬΄μνμλ λ©λλ€. λ€λ₯Έ μ¬λμ΄ μ€μλ‘ κ·νμ μ΄λ©μΌ μ£Όμλ₯Ό μλ ₯νμ μ μμ΅λλ€.</p>"
        message += "<p>κ°μ¬ν©λλ€ Dotori κ³μ  ν</p>"
        if (receivers == null) {
            log.error("λ©μΌμ μ μ‘ν  λμμ΄ μμ΅λλ€: [{}]", subject)
            return
        }
        val senderDto: SenderDto = SenderDto(
            from = from,
            to = receivers,
            subject = subject,
            content = message,
        )
        val sendEmailResult: SendEmailResult = amazonSimpleEmailService.sendEmail(senderDto.toSendRequestDto())
        if (sendEmailResult.sdkHttpMetadata.httpStatusCode === 200) {
            log.info("[AWS SES] λ©μΌμ μ‘μλ£ => $senderDto")
        } else {
            log.error("[AWS SES] λ©μΌμ μ‘ μ€ μλ¬κ° λ°μνμ΅λλ€: {}", sendEmailResult.sdkResponseMetadata.toString())
            log.error("λ°μ‘μ€ν¨ λμμ: " + senderDto.to + " / subject: " + senderDto.subject)
        }
    }

}