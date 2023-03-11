package com.dotori.v2.domain.member.presentation

import com.dotori.v2.domain.member.presentation.data.req.NewPasswordReqDto
import com.dotori.v2.domain.member.presentation.data.req.WithdrawalReqDto
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
    private val changePasswordService: ChangePasswordService,
) {
    @DeleteMapping("/logout")
    fun logout(): ResponseEntity<Void> =
        logoutService.execute()
            .run { ResponseEntity.ok().build() }

    @PostMapping("/withdrawal")
    fun withdrawal(@Valid @RequestBody withdrawalReqDto: WithdrawalReqDto): ResponseEntity<Void> =
        withdrawalService.execute(withdrawalReqDto)
            .run { ResponseEntity.ok().build() }

    @PatchMapping("/password")
    fun changePassword(@Valid @RequestBody newPasswordReqDto: NewPasswordReqDto): ResponseEntity<Void> =
        changePasswordService.execute(newPasswordReqDto)
            .run { ResponseEntity.ok().build() }
}