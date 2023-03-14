package com.dotori.v2.domain.massage.presentation.councillor

import com.dotori.v2.domain.massage.presentation.dto.res.MassageInfoResDto
import com.dotori.v2.domain.massage.presentation.dto.res.MassageMemberListResDto
import com.dotori.v2.domain.massage.service.ApplyMassageService
import com.dotori.v2.domain.massage.service.CancelMassageService
import com.dotori.v2.domain.massage.service.GetMassageInfoService
import com.dotori.v2.domain.massage.service.GetMassageRankService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2/councillor/massage")
class CouncillorMassageController(
    private val applyMassageService: ApplyMassageService,
    private val cancelMassageService: CancelMassageService,
    private val getMassageRankService: GetMassageRankService,
    private val getMassageInfoService: GetMassageInfoService,
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

    @GetMapping
    fun getMassageInfo(): ResponseEntity<MassageInfoResDto> =
        ResponseEntity.ok(getMassageInfoService.execute())
}