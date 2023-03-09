package com.dotori.v2.domain.stu_info.presentation

import com.dotori.v2.domain.stu_info.presentation.data.req.SearchRequestDto
import com.dotori.v2.domain.stu_info.presentation.data.res.FindAllStudentResDto
import com.dotori.v2.domain.stu_info.presentation.data.res.SearchStudentListResDto
import com.dotori.v2.domain.stu_info.service.FindAllMemberService
import com.dotori.v2.domain.stu_info.service.SearchStudentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2/student-info")
class StuInfoController(
    private val findAllMemberService: FindAllMemberService,
    private val searchStudentService: SearchStudentService
) {
    @GetMapping
    fun findAllStudentInfo(): ResponseEntity<List<FindAllStudentResDto>> =
        findAllMemberService.execute()
            .let { ResponseEntity.ok().body(it) }
    
    @GetMapping("/search")
    fun searchStudent(searchRequestDto: SearchRequestDto): List<SearchStudentListResDto> =
        searchStudentService.execute(searchRequestDto)

}
