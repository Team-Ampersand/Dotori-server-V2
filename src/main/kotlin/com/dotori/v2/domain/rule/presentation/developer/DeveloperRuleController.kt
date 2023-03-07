package com.dotori.v2.domain.rule.presentation.developer

import com.dotori.v2.domain.rule.presentation.data.res.RuleListResDto
import com.dotori.v2.domain.rule.service.FindDeveloperRuleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2/developer/rule")
class DeveloperRuleController(
    private val findDeveloperRuleService: FindDeveloperRuleService
) {

    @GetMapping
    fun findViolationOfTheRule(): ResponseEntity<RuleListResDto> =
        ResponseEntity.status(HttpStatus.OK).body(findDeveloperRuleService.execute())

}