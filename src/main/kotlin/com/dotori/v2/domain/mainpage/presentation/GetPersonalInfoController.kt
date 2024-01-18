package com.dotori.v2.domain.mainpage.presentation

import com.dotori.v2.domain.mainpage.presentation.dto.res.PersonalInfoResDto
import com.dotori.v2.domain.mainpage.service.GetPersonalInfoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2/home")
class GetPersonalInfoController(
    private val getPersonalInfoService: GetPersonalInfoService
) {
    @GetMapping
    fun getPersonalInfo(): ResponseEntity<PersonalInfoResDto> =
        ResponseEntity.ok(getPersonalInfoService.execute())
}