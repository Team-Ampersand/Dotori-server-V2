package com.dotori.v2.domain.rule.presentation.admin

import com.dotori.v2.domain.rule.presentation.data.req.RuleGrantReqDto
import com.dotori.v2.domain.rule.service.InsertRuleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2/admin/rule")
class AdminRuleController(
    private val insertRuleService: InsertRuleService
) {

    @PostMapping
    fun insertRule(@RequestBody ruleGrantReqDto: RuleGrantReqDto): ResponseEntity<Void> =
        insertRuleService.execute(ruleGrantReqDto)
            .run { ResponseEntity.status(HttpStatus.CREATED).build() }
}