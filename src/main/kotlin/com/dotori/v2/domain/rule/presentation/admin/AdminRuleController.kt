package com.dotori.v2.domain.rule.presentation.admin

import com.dotori.v2.domain.rule.presentation.data.req.RuleGrantReqDto
import com.dotori.v2.domain.rule.presentation.data.req.StudentListReqDto
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
    private val findAdminRuleService: FindAdminRuleService,
    private val findStudentService: FindStudentService,
    private val deleteRuleService: DeleteRuleService
) {

    @PostMapping
    fun insertRule(@RequestBody ruleGrantReqDto: RuleGrantReqDto): ResponseEntity<Void> =
        insertRuleService.execute(ruleGrantReqDto)
            .run { ResponseEntity.status(HttpStatus.CREATED).build() }

    @GetMapping("/{stuMum}")
    fun findViolationOfTheRule(@PathVariable stuMum: String): ResponseEntity<RuleListResDto> =
        ResponseEntity.status(HttpStatus.OK).body(findAdminRuleService.execute(stuMum))

    @GetMapping
    fun findStudentByMemberNameAndClassId(studentListReqDto: StudentListReqDto): ResponseEntity<MemberListResDto> =
        ResponseEntity.status(HttpStatus.OK).body(findStudentService.execute(studentListReqDto = studentListReqDto))

    @DeleteMapping("/{ruleId}")
    fun deleteRule(@PathVariable ruleId: Long): ResponseEntity<Void> =
        deleteRuleService.execute(ruleId)
            .run { ResponseEntity.status(HttpStatus.OK).build() }

}