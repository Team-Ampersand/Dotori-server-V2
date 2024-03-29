package com.dotori.v2.domain.selfstudy.presentation.member

import com.dotori.v2.domain.selfstudy.presentation.dto.res.SelfStudyInfoResDto
import com.dotori.v2.domain.selfstudy.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.selfstudy.service.*
import com.dotori.v2.domain.selfstudy.presentation.dto.req.SelfStudySearchReqDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/v2/member/self-study")
class MemberSelfStudyController(
    private val applySelfStudyService: ApplySelfStudyService,
    private val getSelfStudyInfoService: GetSelfStudyInfoService,
    private val getSelfStudyRankService: GetSelfStudyRankService,
    private val cancelSelfStudyService: CancelSelfStudyService,
    private val getSelfStudyByStuNumAndNameService: GetSelfStudyByStuNumAndNameService
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
}