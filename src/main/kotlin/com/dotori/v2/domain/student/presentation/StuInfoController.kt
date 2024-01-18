package com.dotori.v2.domain.student.presentation

import com.dotori.v2.domain.student.presentation.data.req.ModifyStudentInfoRequest
import com.dotori.v2.domain.student.presentation.data.req.SearchRequestDto
import com.dotori.v2.domain.student.presentation.data.res.FindAllStudentResDto
import com.dotori.v2.domain.student.presentation.data.res.SearchStudentListResDto
import com.dotori.v2.domain.student.service.FindAllMemberService
import com.dotori.v2.domain.student.service.ModifyStudentInfoService
import com.dotori.v2.domain.student.service.SearchStudentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v2/student-info")
class StuInfoController(
    private val findAllMemberService: FindAllMemberService,
    private val searchStudentService: SearchStudentService,
    private val modifyStudentInfoService: ModifyStudentInfoService
) {
    @GetMapping
    fun findAllStudentInfo(): ResponseEntity<List<FindAllStudentResDto>> =
        findAllMemberService.execute()
            .let { ResponseEntity.ok().body(it) }

    @GetMapping("/search")
    fun searchStudent(searchRequestDto: SearchRequestDto): ResponseEntity<List<SearchStudentListResDto>> =
        searchStudentService.execute(searchRequestDto)
            .let { ResponseEntity.ok().body(it) }

    @PutMapping("/modify")
    fun modifyStudentInfo(@RequestBody modifyStudentInfoRequest: ModifyStudentInfoRequest) =
        modifyStudentInfoService.execute(modifyStudentInfoRequest)

}
