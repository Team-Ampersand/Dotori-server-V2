package com.dotori.v2.domain.self_study.presentation.councillor

import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyInfoResDto
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.self_study.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v2/councillor/self-study")
class CouncillorSelfStudyController(
    private val applySelfStudyService: ApplySelfStudyService,
    private val getSelfStudyInfoService: GetSelfStudyInfoService,
    private val getSelfStudyRankService: GetSelfStudyRankService,
    private val cancelSelfStudyService: CancelSelfStudyService,
    private val getSelfStudyByMemberNameService: GetSelfStudyByMemberNameService,
    private val getSelfStudyByStuNumService: GetSelfStudyByStuNumService,
    private val banSelfStudyService: BanSelfStudyService,
    private val cancelBanSelfStudyService: CancelBanSelfStudyService
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

    @GetMapping("/rank")
    fun getSelfStudyRank(): ResponseEntity<SelfStudyMemberListResDto> =
        ResponseEntity.ok(getSelfStudyRankService.execute())

    @GetMapping
    fun getSelfStudyByMemberName(@RequestParam memberName: String): ResponseEntity<SelfStudyMemberListResDto> =
        ResponseEntity.ok(getSelfStudyByMemberNameService.execute(memberName))

    @GetMapping("/{classId}")
    fun getSelfStudyByStuNum(@PathVariable classId: String): ResponseEntity<SelfStudyMemberListResDto> =
        ResponseEntity.ok(getSelfStudyByStuNumService.execute(classId))

    @PutMapping("/ban/{user_id}")
    fun banSelfStudyCouncillor(@PathVariable("user_id") id: Long): ResponseEntity<Void> =
        banSelfStudyService.execute(id)
            .run { ResponseEntity.ok().build() }

    @PutMapping("/ban/cancel/{user_id}")
    fun cancelBanSelfStudyCouncillor(@PathVariable("user_id") id: Long): ResponseEntity<Void> =
        cancelBanSelfStudyService.execute(id)
            .run { ResponseEntity.ok().build() }
}