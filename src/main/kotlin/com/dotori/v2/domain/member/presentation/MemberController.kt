package com.dotori.v2.domain.member.presentation

import com.dotori.v2.domain.member.presentation.dto.req.WithdrawalReqDto
import com.dotori.v2.domain.member.service.LogoutService
import com.dotori.v2.domain.member.service.WithdrawalService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v2/member")
class MemberController(
    private val logoutService: LogoutService,
    private val withdrawalService: WithdrawalService,
) {
    @DeleteMapping("/logout")
    fun logout(): ResponseEntity<Void> =
        logoutService.execute()
            .run { ResponseEntity.ok().build() }

    @PostMapping("/withdrawal")
    fun withdrawal(@Valid @RequestBody withdrawalReqDto: WithdrawalReqDto): ResponseEntity<Void> =
        withdrawalService.execute(withdrawalReqDto)
            .run { ResponseEntity.ok().build() }
}