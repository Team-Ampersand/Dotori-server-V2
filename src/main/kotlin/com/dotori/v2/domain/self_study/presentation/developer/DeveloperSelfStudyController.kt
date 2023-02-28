package com.dotori.v2.domain.self_study.presentation.developer

import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyInfoResDto
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.self_study.service.ApplySelfStudyService
import com.dotori.v2.domain.self_study.service.GetSelfStudyInfoService
import com.dotori.v2.domain.self_study.service.GetSelfStudyRankService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2/developer/self-study")
class DeveloperSelfStudyController(
    private val applySelfStudyService: ApplySelfStudyService,
    private val getSelfStudyInfoService: GetSelfStudyInfoService,
    private val getSelfStudyRankService: GetSelfStudyRankService
) {
    @PostMapping
    fun applySelfStudy(): ResponseEntity<Void> =
        applySelfStudyService.execute()
            .run { ResponseEntity.ok().build() }

    @GetMapping("/info")
    fun getSelfStudyInfo(): ResponseEntity<SelfStudyInfoResDto> =
        ResponseEntity.ok(getSelfStudyInfoService.execute())

    @GetMapping
    fun getSelfStudyRank(): ResponseEntity<SelfStudyMemberListResDto> =
        ResponseEntity.ok(getSelfStudyRankService.execute())
}