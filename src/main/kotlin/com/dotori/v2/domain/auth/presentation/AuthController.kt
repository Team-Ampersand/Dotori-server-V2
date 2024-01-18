package com.dotori.v2.domain.auth.presentation

import com.dotori.v2.domain.auth.util.AuthConverter
import com.dotori.v2.domain.auth.presentation.data.req.SignInGAuthReqDto
import com.dotori.v2.domain.auth.presentation.data.req.SignInEmailAndPasswordReqDto
import com.dotori.v2.domain.auth.presentation.data.req.SignUpReqDto
import com.dotori.v2.domain.auth.presentation.data.res.RefreshResDto
import com.dotori.v2.domain.auth.presentation.data.res.SignInResDto
import com.dotori.v2.domain.auth.service.*
import com.dotori.v2.domain.member.presentation.data.req.NoAuthNewPasswordReqDto
import com.dotori.v2.domain.member.service.ChangePasswordService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v2/auth")
class AuthController (
    private val signUpService: SignUpService,
    private val signInGAuthService: SignInGAuthService,
    private val signInEmailAndPasswordService: SignInEmailAndPasswordService,
    private val refreshTokenService: RefreshTokenService,
    private val changePasswordService: ChangePasswordService,
    private val logoutService: LogoutService,
    private val authConverter: AuthConverter,
) {
    @PostMapping("/signup")
    fun signup(@Valid @RequestBody signupReqDto: SignUpReqDto): ResponseEntity<Void> =
        signUpService.execute(signupReqDto)
            .run { ResponseEntity.ok().build() }

    @PostMapping("/gauth")
    fun signInGAuth(@Valid @RequestBody signInGAuthReqDto: SignInGAuthReqDto): ResponseEntity<SignInResDto> =
        authConverter.toDto(signInGAuthReqDto)
            .let { ResponseEntity.ok(signInGAuthService.execute(it)) }

    @PostMapping
    fun signInEmailAndPassword(@Valid @RequestBody signInEmailAndPasswordReqDto: SignInEmailAndPasswordReqDto): ResponseEntity<SignInResDto> =
        authConverter.toDto(signInEmailAndPasswordReqDto)
            .let { ResponseEntity.ok(signInEmailAndPasswordService.execute(it)) }

    @PatchMapping
    fun getNewRefreshToken(@RequestHeader("Refresh-Token") refreshToken: String): ResponseEntity<RefreshResDto> =
        ResponseEntity.ok().body(refreshTokenService.execute(refreshToken = refreshToken))

    @DeleteMapping("/logout")
    fun logout(): ResponseEntity<Void> =
        logoutService.execute()
            .run { ResponseEntity.ok().build() }

    @PatchMapping("/password")
    fun changePassword(@Valid @RequestBody newPasswordReqDto: NoAuthNewPasswordReqDto): ResponseEntity<Void> =
        changePasswordService.execute(newPasswordReqDto)
            .run { ResponseEntity.ok().build() }

}