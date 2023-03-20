package com.dotori.v2.domain.massage.presentation.admin

import com.dotori.v2.domain.massage.presentation.dto.req.MassageLimitReqDto
import com.dotori.v2.domain.massage.presentation.dto.res.MassageInfoResDto
import com.dotori.v2.domain.massage.presentation.dto.res.MassageMemberListResDto
import com.dotori.v2.domain.massage.service.GetMassageInfoService
import com.dotori.v2.domain.massage.service.GetMassageRankService
import com.dotori.v2.domain.massage.service.UpdateMassageLimitService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/v2/admin/massage")
class AdminMassageController(
    private val getMassageRankService: GetMassageRankService,
    private val getMassageInfoService: GetMassageInfoService,
    private val updateMassageLimitService: UpdateMassageLimitService
) {
    @GetMapping("/rank")
    fun getMassageRank(): ResponseEntity<MassageMemberListResDto> =
        ResponseEntity.ok(getMassageRankService.execute())

    @GetMapping
    fun getMassageInfo(): ResponseEntity<MassageInfoResDto> =
        ResponseEntity.ok(getMassageInfoService.execute())

    @PatchMapping("/limit")
    fun updateMassageLimit(@Valid @RequestBody massageLimitReqDto: MassageLimitReqDto): ResponseEntity<Void> =
        updateMassageLimitService.execute(massageLimitReqDto)
            .run { ResponseEntity.ok().build() }
}