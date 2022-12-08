package com.dotori.v2.domain.email.presentation

import com.dotori.v2.domain.email.presentation.dto.request.EmailReqDto
import com.dotori.v2.domain.email.service.PasswordChangeEmailSendService
import com.dotori.v2.domain.email.service.SingupEmailSendService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2/email")
class EmailSendController(
    private val singupEmailSendService: SingupEmailSendService,
    private val passwordChangeEmailSendService: PasswordChangeEmailSendService
){
    @PostMapping("/signup")
    fun sendEmailSignup(@RequestBody emailReqDto: EmailReqDto): ResponseEntity<Void>{
        singupEmailSendService.execute(emailReqDto)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/password")
    fun sendEmailChangePassword(@RequestBody emailReqDto: EmailReqDto): ResponseEntity<Void>{
        passwordChangeEmailSendService.execute(emailReqDto)
        return ResponseEntity.ok().build()
    }
}