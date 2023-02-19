package com.dotori.v2.domain.self_study.presentation.developer

import com.dotori.v2.domain.self_study.service.ApplySelfStudyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2/developer/self_study")
class DeveloperSelfStudyController(
    private val applySelfStudyService: ApplySelfStudyService
) {
    @PostMapping
    fun applySelfStudy(): ResponseEntity<Void> =
        applySelfStudyService.execute()
            .run { ResponseEntity.ok().build() }
}