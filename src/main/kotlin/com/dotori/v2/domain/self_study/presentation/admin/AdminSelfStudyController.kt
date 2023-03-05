package com.dotori.v2.domain.self_study.presentation.admin

import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyInfoResDto
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.self_study.service.GetSelfStudyByMemberNameService
import com.dotori.v2.domain.self_study.service.GetSelfStudyByStuNumService
import com.dotori.v2.domain.self_study.service.GetSelfStudyInfoService
import com.dotori.v2.domain.self_study.service.GetSelfStudyRankService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v2/admin/self-study")
class AdminSelfStudyController(
    private val getSelfStudyInfoService: GetSelfStudyInfoService,
    private val getSelfStudyRankService: GetSelfStudyRankService,
    private val getSelfStudyByMemberNameService: GetSelfStudyByMemberNameService,
    private val getSelfStudyByStuNumService: GetSelfStudyByStuNumService
) {

    @GetMapping("/info")
    fun getSelfStudyInfo(): ResponseEntity<SelfStudyInfoResDto> =
        ResponseEntity.ok(getSelfStudyInfoService.execute())

    @GetMapping("/rank")
    fun getSelfStudyRank(): ResponseEntity<SelfStudyMemberListResDto> =
        ResponseEntity.ok(getSelfStudyRankService.execute())

    @GetMapping
    fun getSelfStudyByMemberName(@RequestParam memberName: String): ResponseEntity<SelfStudyMemberListResDto> =
        ResponseEntity.ok(getSelfStudyByMemberNameService.execute(memberName))

    @GetMapping("/{classId}")
    fun getSelfStudyByStuNum(@PathVariable classId: String): ResponseEntity<SelfStudyMemberListResDto> =
        ResponseEntity.ok(getSelfStudyByStuNumService.execute(classId))
}