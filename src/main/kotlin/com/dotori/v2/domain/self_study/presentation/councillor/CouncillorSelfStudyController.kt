package com.dotori.v2.domain.self_study.presentation.councillor

import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyInfoResDto
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.self_study.service.ApplySelfStudyService
import com.dotori.v2.domain.self_study.service.CancelSelfStudyService
import com.dotori.v2.domain.self_study.service.GetSelfStudyInfoService
import com.dotori.v2.domain.self_study.service.GetSelfStudyRankService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2/councillor/self-study")
class CouncillorSelfStudyController(
    private val applySelfStudyService: ApplySelfStudyService,
    private val getSelfStudyInfoService: GetSelfStudyInfoService,
    private val getSelfStudyRankService: GetSelfStudyRankService,
    private val cancelSelfStudyService: CancelSelfStudyService
) {
    @PostMapping
    fun applySelfStudy(): ResponseEntity<Void> =
        applySelfStudyService.execute()
            .run { ResponseEntity.ok().build() }

    @DeleteMapping
    fun cancelSelfStudy(): ResponseEntity<Void> =
        cancelSelfStudyService.execute()
            .run { ResponseEntity.ok().build() }

    @GetMapping("/info")
    fun getSelfStudyInfo(): ResponseEntity<SelfStudyInfoResDto> =
        ResponseEntity.ok(getSelfStudyInfoService.execute())

    @GetMapping
    fun getSelfStudyRank(): ResponseEntity<SelfStudyMemberListResDto> =
        ResponseEntity.ok(getSelfStudyRankService.execute())
}