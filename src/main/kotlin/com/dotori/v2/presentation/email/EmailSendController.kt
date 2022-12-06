package com.dotori.v2.presentation.email

import com.dotori.v2.application.email.usecase.EmailSendUseCase
import com.dotori.v2.application.email.usecase.PasswordChangeEmailSendUseCase
import com.dotori.v2.application.email.usecase.SignupEmailSendUseCase
import com.dotori.v2.presentation.email.dto.request.EmailReqDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2/email")
class EmailSendController(
    private val emailSendUseCase: EmailSendUseCase,
    private val passwordChangeEmailSendUseCase: PasswordChangeEmailSendUseCase,
    private val signupEmailSendUseCase: SignupEmailSendUseCase,
){
    @PostMapping("/signup")
    fun sendEmailSignup(@RequestBody emailReqDto: EmailReqDto): ResponseEntity<Void>{
        signupEmailSendUseCase.execute(emailReqDto)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/password")
    fun sendEmailChangePassword(@RequestBody emailReqDto: EmailReqDto): ResponseEntity<Void>{
        passwordChangeEmailSendUseCase.execute(emailReqDto)
        return ResponseEntity.ok().build()
    }
}