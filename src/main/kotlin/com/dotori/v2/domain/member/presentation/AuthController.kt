package com.dotori.v2.domain.member.presentation

import com.dotori.v2.domain.member.presentation.dto.SignupReqDto
import com.dotori.v2.domain.member.service.SignupService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/v2/auth")
class AuthController(
    private val signupService: SignupService,
){
    @PostMapping
    fun signup(@Valid @RequestBody signupReqDto: SignupReqDto): ResponseEntity<Void> {
        signupService.execute(signupReqDto)
        return ResponseEntity.ok().build()
    }
}