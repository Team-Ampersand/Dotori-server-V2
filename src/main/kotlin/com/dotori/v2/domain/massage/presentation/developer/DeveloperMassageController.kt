package com.dotori.v2.domain.massage.presentation.developer

import com.dotori.v2.domain.massage.presentation.dto.req.MassageLimitReqDto
import com.dotori.v2.domain.massage.presentation.dto.res.MassageInfoResDto
import com.dotori.v2.domain.massage.presentation.dto.res.MassageMemberListResDto
import com.dotori.v2.domain.massage.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v2/developer/massage")
class DeveloperMassageController(
    private val applyMassageService: ApplyMassageService,
    private val cancelMassageService: CancelMassageService,
    private val getMassageRankService: GetMassageRankService,
    private val getMassageInfoService: GetMassageInfoService,
    private val updateMassageLimitService: UpdateMassageLimitService
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

    @PatchMapping("/limit")
    fun updateMassageLimit(@RequestBody massageLimitReqDto: MassageLimitReqDto): ResponseEntity<Void> =
        updateMassageLimitService.execute(massageLimitReqDto)
            .run { ResponseEntity.ok().build() }
}