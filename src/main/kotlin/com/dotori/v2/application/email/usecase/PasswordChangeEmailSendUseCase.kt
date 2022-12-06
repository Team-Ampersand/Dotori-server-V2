package com.dotori.v2.application.email.usecase

import com.dotori.v2.application.member.port.ExistMemberPort
import com.dotori.v2.infraStructure.global.annotation.UseCase
import com.dotori.v2.presentation.email.dto.request.EmailReqDto

@UseCase
class PasswordChangeEmailSendUseCase(
    private val emailSendUseCase: EmailSendUseCase,
    private val existMemberPort: ExistMemberPort,
){
    fun execute(emailReqDto: EmailReqDto): String{
        if(!existMemberPort.isExist(emailReqDto.email))
            throw RuntimeException()
        return emailSendUseCase.execute(emailReqDto)
    }
}