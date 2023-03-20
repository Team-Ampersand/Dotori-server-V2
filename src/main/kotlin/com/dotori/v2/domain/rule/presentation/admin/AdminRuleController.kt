package com.dotori.v2.domain.rule.presentation.admin

import com.dotori.v2.domain.rule.presentation.data.req.RuleGrantReqDto
import com.dotori.v2.domain.rule.presentation.data.res.MemberListResDto
import com.dotori.v2.domain.rule.presentation.data.res.RuleListResDto
import com.dotori.v2.domain.rule.service.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v2/admin/rule")
class AdminRuleController(
    private val insertRuleService: InsertRuleService,
    private val findAllAdminRuleService: FindAllAdminRuleService,
    private val findAdminRuleService: FindAdminRuleService,
    private val findStudentService: FindStudentService,
    private val deleteRuleService: DeleteRuleService
) {

    @PostMapping
    fun insertRule(@RequestBody ruleGrantReqDto: RuleGrantReqDto): ResponseEntity<Void> =
        insertRuleService.execute(ruleGrantReqDto)
            .run { ResponseEntity.status(HttpStatus.CREATED).build() }

    @GetMapping("/all/{stuNum}")
    fun findAllViolationOfTheRule(@PathVariable stuNum: String): ResponseEntity<RuleListResDto> =
        ResponseEntity.status(HttpStatus.OK).body(findAllAdminRuleService.execute(stuNum))

    @GetMapping("/{stuMum}")
    fun findViolationOfTheRule(@PathVariable stuMum: String): ResponseEntity<RuleListResDto> =
        ResponseEntity.status(HttpStatus.OK).body(findAdminRuleService.execute(stuMum))

    @GetMapping
    fun findStudentByMemberNameAndClassId(
        @RequestParam(value = "memberName", required = false) memberName: String?,
        @RequestParam(value = "stuNum", required = false) stuNum: String?
    ): ResponseEntity<MemberListResDto> =
        ResponseEntity.status(HttpStatus.OK).body(findStudentService.execute(stuNum = stuNum, memberName = memberName))

    @DeleteMapping("/{rule_id}")
    fun deleteRule(@PathVariable rule_id: Long): ResponseEntity<Void> =
        deleteRuleService.execute(rule_id)
            .run { ResponseEntity.status(HttpStatus.OK).build() }

}