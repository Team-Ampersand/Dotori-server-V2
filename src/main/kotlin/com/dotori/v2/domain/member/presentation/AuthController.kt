package com.dotori.v2.domain.member.presentation

import com.dotori.v2.domain.auth.util.AuthConverter
import com.dotori.v2.domain.member.presentation.data.req.SignInReqDto
import com.dotori.v2.domain.member.presentation.data.res.RefreshResDto
import com.dotori.v2.domain.member.presentation.data.res.SignInResDto
import com.dotori.v2.domain.member.service.RefreshTokenService
import com.dotori.v2.domain.member.service.SignInService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v2/auth")
class AuthController (
    private val signInService: SignInService,
    private val refreshTokenService: RefreshTokenService,
    private val authConverter: AuthConverter
) {

    @PostMapping
    fun signIn(@Valid @RequestBody signInReqDto: SignInReqDto): ResponseEntity<SignInResDto> =
        authConverter.toDto(signInReqDto)
            .let { ResponseEntity.ok(signInService.execute(it)) }

    @PatchMapping
    fun getNewRefreshToken(@RequestHeader("Refresh-Token") refreshToken: String): ResponseEntity<RefreshResDto> =
        ResponseEntity.ok().body(refreshTokenService.execute(refreshToken = refreshToken))
}