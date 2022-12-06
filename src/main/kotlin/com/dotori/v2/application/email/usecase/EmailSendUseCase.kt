package com.dotori.v2.application.email.usecase

import com.dotori.v2.application.email.port.DeleteEmailCertificatePort
import com.dotori.v2.application.email.port.SaveEmailCertificatePort
import com.dotori.v2.application.member.port.ExistMemberPort
import com.dotori.v2.domain.email.EmailCertificate
import com.dotori.v2.infraStructure.email.EmailSender
import com.dotori.v2.infraStructure.global.annotation.UseCase
import com.dotori.v2.infraStructure.global.util.KeyUtil
import com.dotori.v2.presentation.email.dto.request.EmailReqDto
import java.time.LocalDateTime

@UseCase
class EmailSendUseCase(
    private val emailSender: EmailSender,
    private val keyUtil: KeyUtil,
    private val deleteEmailCertificatePort: DeleteEmailCertificatePort,
    private val saveEmailCertificatePort: SaveEmailCertificatePort,
){
    fun execute(emailReqDto: EmailReqDto): String{
        val key = keyUtil.keyIssuance()
        emailSender.send(emailReqDto.email, key)
        deleteEmailCertificatePort.delete(emailReqDto.email)
        val emailCertificate = EmailCertificate(
            email = emailReqDto.email,
            key = key,
            expiredTime = LocalDateTime.now().plusMinutes(5)
        )
        saveEmailCertificatePort.save(emailCertificate)
        return key
    }
}