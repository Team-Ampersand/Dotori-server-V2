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
        val subject = "🎈[DOTORI] 인증 키"
        var message = "<p style=\"color:blueviolet\">안녕하세요 Dotori 계정에 사용할 일회용 코드에 대한 요청을 받았습니다.</p>"
        message += "<p>일회용 코드: $key</p>"
        message += "<p>이 코드를 요청하지 않은 경우 이 메일을 무시하셔도 됩니다. 다른 사람이 실수로 귀하의 이메일 주소를 입력했을 수 있습니다.</p>"
        message += "<p>감사합니다 Dotori 계정 팀</p>"
        if (receivers == null) {
            log.error("메일을 전송할 대상이 없습니다: [{}]", subject)
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
            log.info("[AWS SES] 메일전송완료 => $senderDto")
        } else {
            log.error("[AWS SES] 메일전송 중 에러가 발생했습니다: {}", sendEmailResult.sdkResponseMetadata.toString())
            log.error("발송실패 대상자: " + senderDto.to + " / subject: " + senderDto.subject)
        }
    }

}