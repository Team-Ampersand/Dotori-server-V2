package com.dotori.v2.domain.rule.presentation.member

import com.dotori.v2.domain.rule.presentation.data.res.RuleListResDto
import com.dotori.v2.domain.rule.service.FindMemberRuleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2/member/rule")
class MemberRuleController(
    private val findMemberRuleService: FindMemberRuleService
) {

    @GetMapping
    fun findRuleAtMainPageMember(): ResponseEntity<RuleListResDto> =
        ResponseEntity.ok().body(findMemberRuleService.execute())

}