package com.dotori.v2.application.email.usecase

import com.dotori.v2.application.member.port.ExistMemberPort
import com.dotori.v2.infraStructure.global.annotation.UseCase
import com.dotori.v2.presentation.email.dto.request.EmailReqDto

@UseCase
class SignupEmailSendUseCase(
    private val existMemberPort: ExistMemberPort,
    private val emailSendUseCase: EmailSendUseCase,
){
    fun execute(emailReqDto: EmailReqDto): String{
        if(existMemberPort.isExist(emailReqDto.email))
            throw RuntimeException()
        return emailSendUseCase.execute(emailReqDto)
    }
}