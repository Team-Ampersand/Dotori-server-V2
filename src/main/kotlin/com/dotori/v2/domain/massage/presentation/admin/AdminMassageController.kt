package com.dotori.v2.domain.massage.presentation.admin

import com.dotori.v2.domain.massage.presentation.dto.res.MassageMemberListResDto
import com.dotori.v2.domain.massage.service.GetMassageRankService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2/admin/massage")
class AdminMassageController(
    private val getMassageRankService: GetMassageRankService
) {
    @GetMapping("/rank")
    fun getMassageRank(): ResponseEntity<MassageMemberListResDto> =
        ResponseEntity.ok(getMassageRankService.execute())
}