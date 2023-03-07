package com.dotori.v2.domain.rule.presentation.councillor

import com.dotori.v2.domain.rule.presentation.data.res.RuleListResDto
import com.dotori.v2.domain.rule.service.FindCouncillorRuleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2/councillor/rule")
class CouncillorRuleController(
    private val findCouncillorRuleService: FindCouncillorRuleService
) {

    @GetMapping()
    fun findViolationOfTheRule(): ResponseEntity<RuleListResDto> =
        ResponseEntity.status(HttpStatus.OK).body(findCouncillorRuleService.execute())
}