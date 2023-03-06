package com.dotori.v2.domain.massage.presentation.member

import com.dotori.v2.domain.massage.presentation.dto.res.MassageMemberListResDto
import com.dotori.v2.domain.massage.service.ApplyMassageService
import com.dotori.v2.domain.massage.service.CancelMassageService
import com.dotori.v2.domain.massage.service.GetMassageRankService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v2/member/massage")
class MemberMassageController(
    private val applyMassageService: ApplyMassageService,
    private val cancelMassageService: CancelMassageService,
    private val getMassageRankService: GetMassageRankService
) {
    @PostMapping
    fun applyMassage(): ResponseEntity<Void> =
        applyMassageService.execute()
            .run { ResponseEntity.ok().build() }
    
    @DeleteMapping
    fun cancelMassage(): ResponseEntity<Void> =
        cancelMassageService.execute()
            .run { ResponseEntity.ok().build() }
    
    @GetMapping("/rank")
    fun getMassageRank(): ResponseEntity<MassageMemberListResDto> =
        ResponseEntity.ok(getMassageRankService.execute())
}