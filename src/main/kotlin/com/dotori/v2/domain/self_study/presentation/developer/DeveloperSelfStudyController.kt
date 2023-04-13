package com.dotori.v2.domain.self_study.presentation.developer

import com.dotori.v2.domain.self_study.presentation.dto.req.SelfStudyCheckReqDto
import com.dotori.v2.domain.self_study.presentation.dto.req.SelfStudyLimitReqDto
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyInfoResDto
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.self_study.service.*
import com.dotori.v2.domain.stu_info.exception.SelfStudySearchReqDto
import com.dotori.v2.domain.stu_info.presentation.data.req.SearchRequestDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v2/developer/self-study")
class DeveloperSelfStudyController(
    private val applySelfStudyService: ApplySelfStudyService,
    private val getSelfStudyInfoService: GetSelfStudyInfoService,
    private val getSelfStudyRankService: GetSelfStudyRankService,
    private val cancelSelfStudyService: CancelSelfStudyService,
    private val cancelBanSelfStudyService: CancelBanSelfStudyService,
    private val banSelfStudyService: BanSelfStudyService,
    private val changeSelfStudyLimitService: ChangeSelfStudyLimitService,
    private val getSelfStudyByStuNumAndNameService: GetSelfStudyByStuNumAndNameService,
    private val updateSelfStudyCheckService: UpdateSelfStudyCheckService
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

    @GetMapping("/search")
    fun searchSelfStudy(searchRequestDto: SelfStudySearchReqDto): ResponseEntity<SelfStudyMemberListResDto> =
        ResponseEntity.ok(getSelfStudyByStuNumAndNameService.execute(searchRequestDto))

    @PutMapping("/ban/{user_id}")
    fun banSelfStudyDeveloper(@PathVariable("user_id") id: Long): ResponseEntity<Void> =
        banSelfStudyService.execute(id)
            .run { ResponseEntity.ok().build() }

    @PutMapping("/ban/cancel/{user_id}")
    fun cancelBanSelfStudyDeveloper(@PathVariable("user_id") id: Long): ResponseEntity<Void> =
        cancelBanSelfStudyService.execute(id)
            .run { ResponseEntity.ok().build() }

    @PatchMapping("/limit")
    fun updateSelfStudyLimit(@RequestBody changeSelfStudyLimitReqDto: SelfStudyLimitReqDto): ResponseEntity<Void> =
        changeSelfStudyLimitService.execute(changeSelfStudyLimitReqDto)
            .run { ResponseEntity.ok().build() }

    @PatchMapping("/check/{memberId}")
    fun updateSelfStudyCheck(@PathVariable memberId: Long,@Valid @RequestBody selfStudyCheckReqDto: SelfStudyCheckReqDto): ResponseEntity<Void> =
        updateSelfStudyCheckService.execute(memberId, selfStudyCheckReqDto)
            .run { ResponseEntity.ok().build() }
}