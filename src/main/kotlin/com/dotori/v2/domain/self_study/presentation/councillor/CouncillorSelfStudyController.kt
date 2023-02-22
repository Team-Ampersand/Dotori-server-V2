package com.dotori.v2.domain.self_study.presentation.councillor

import com.dotori.v2.domain.self_study.presentation.dto.SelfStudyInfoResDto
import com.dotori.v2.domain.self_study.service.ApplySelfStudyService
import com.dotori.v2.domain.self_study.service.GetSelfStudyInfoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2/member/self_study")
class CouncillorSelfStudyController(
    private val applySelfStudyService: ApplySelfStudyService,
    private val getSelfStudyInfoService: GetSelfStudyInfoService,
) {
    @PostMapping
    fun applySelfStudy(): ResponseEntity<Void> =
        applySelfStudyService.execute()
            .run { ResponseEntity.ok().build() }

    @GetMapping("/info")
    fun getSelfStudyInfo(): ResponseEntity<SelfStudyInfoResDto> =
        ResponseEntity.ok(getSelfStudyInfoService.execute())
}