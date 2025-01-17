package com.dotori.v2.domain.student.presentation

import com.dotori.v2.domain.student.presentation.data.req.ModifyStudentInfoRequest
import com.dotori.v2.domain.student.presentation.data.req.SearchRequestDto
import com.dotori.v2.domain.student.presentation.data.res.FindAllStudentResDto
import com.dotori.v2.domain.student.presentation.data.res.SearchStudentListResDto
import com.dotori.v2.domain.student.service.FindAllMemberService
import com.dotori.v2.domain.student.service.ModifyStudentInfoService
import com.dotori.v2.domain.student.service.SearchStudentService
import com.dotori.v2.domain.student.task.SyncStudentNumberTask
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v2/student-info")
class StuInfoController(
    private val findAllMemberService: FindAllMemberService,
    private val searchStudentService: SearchStudentService,
    private val modifyStudentInfoService: ModifyStudentInfoService,
    private val syncStudentNumberTask: SyncStudentNumberTask
) {

    @GetMapping
    fun findAllStudentInfo(): ResponseEntity<List<FindAllStudentResDto>> =
        findAllMemberService.execute()
            .let { ResponseEntity.ok().body(it.students) }

    @GetMapping("/search")
    fun searchStudent(searchRequestDto: SearchRequestDto): ResponseEntity<List<SearchStudentListResDto>> =
        searchStudentService.execute(searchRequestDto)
            .let { ResponseEntity.ok().body(it) }

    @PutMapping("/modify")
    fun modifyStudentInfo(@RequestBody modifyStudentInfoRequest: ModifyStudentInfoRequest): ResponseEntity<Void> =
        modifyStudentInfoService.execute(modifyStudentInfoRequest)
            .let { ResponseEntity.ok().build() }


    @PostMapping("/sync")
    fun syncStudentNumber(@RequestParam("csv") csv: MultipartFile): ResponseEntity<Void> =
        syncStudentNumberTask.syncStudentNumber(csv)
            .let { ResponseEntity.ok().build() }

}
