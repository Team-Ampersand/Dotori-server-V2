package com.dotori.v2.domain.member.presentation

import com.dotori.v2.domain.member.presentation.data.req.NewPasswordReqDto
import com.dotori.v2.domain.member.presentation.data.req.WithdrawalReqDto
import com.dotori.v2.domain.member.service.ChangeAuthPasswordService
import com.dotori.v2.domain.member.service.ChangePasswordService
import com.dotori.v2.domain.member.service.LogoutService
import com.dotori.v2.domain.member.service.WithdrawalService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v2/members")
class MemberController(
    private val logoutService: LogoutService,
    private val withdrawalService: WithdrawalService,
    private val changeAuthPasswordService: ChangeAuthPasswordService
) {
    @DeleteMapping("/logout")
    fun logout(): ResponseEntity<Void> =
        logoutService.execute()
            .run { ResponseEntity.ok().build() }

    @DeleteMapping("/withdrawal")
    fun withdrawal(): ResponseEntity<Void> =
        withdrawalService.execute()
            .run { ResponseEntity.ok().build() }

    @PatchMapping("/password")
    fun changePassword(@Valid @RequestBody newPasswordReqDto: NewPasswordReqDto): ResponseEntity<Void> =
        changeAuthPasswordService.execute(newPasswordReqDto)
            .run { ResponseEntity.ok().build() }

}