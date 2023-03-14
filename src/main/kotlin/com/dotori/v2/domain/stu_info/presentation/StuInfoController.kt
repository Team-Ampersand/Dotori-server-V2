package com.dotori.v2.domain.stu_info.presentation

import com.dotori.v2.domain.stu_info.presentation.data.req.ModifyStudentInfoRequest
import com.dotori.v2.domain.stu_info.presentation.data.req.SearchRequestDto
import com.dotori.v2.domain.stu_info.presentation.data.res.FindAllStudentResDto
import com.dotori.v2.domain.stu_info.presentation.data.res.SearchStudentListResDto
import com.dotori.v2.domain.stu_info.service.FindAllMemberService
import com.dotori.v2.domain.stu_info.service.ModifyStudentInfoService
import com.dotori.v2.domain.stu_info.service.SearchStudentService
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
