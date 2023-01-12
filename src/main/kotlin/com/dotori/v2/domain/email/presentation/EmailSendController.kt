package com.dotori.v2.domain.email.presentation

import com.dotori.v2.domain.email.presentation.dto.request.EmailCheckReqDto
import com.dotori.v2.domain.email.presentation.dto.request.EmailReqDto
import com.dotori.v2.domain.email.service.EmailCheckService
import com.dotori.v2.domain.email.service.PasswordChangeEmailSendService
import com.dotori.v2.domain.email.service.SingupEmailSendService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2/email")
class EmailController(
    private val signupEmailSendService: SingupEmailSendService,
    private val passwordChangeEmailSendService: PasswordChangeEmailSendService,
    private val emailCheckService: EmailCheckService
){
    @PostMapping("/signup")
    fun sendEmailSignup(@RequestBody emailReqDto: EmailReqDto): ResponseEntity<Void>{
        signupEmailSendService.execute(emailReqDto)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/password")
    fun sendEmailChangePassword(@RequestBody emailReqDto: EmailReqDto): ResponseEntity<Void>{
        passwordChangeEmailSendService.execute(emailReqDto)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/verify-email")
    fun verifyEmail(@RequestBody emailCheckReqDto: EmailCheckReqDto): ResponseEntity<Void>{
        emailCheckService.execute(emailCheckReqDto.key)
        return ResponseEntity.ok().build()
    }
}