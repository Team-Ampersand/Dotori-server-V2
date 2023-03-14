package com.dotori.v2.domain.member.presentation.data.req

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class SignupReqDto(
    @field:NotBlank
    @field:Size(min = 1, max = 10)
    val memberName:String,

    @field:NotBlank
    @field:Size(min = 4, max = 4)
    val stuNum:String,

    @field:NotBlank
    @field:Size(min = 4)
    val password: String,

    @field:NotBlank
    @field:Pattern(regexp = "^[a-zA-Z0-9]+@gsm.hs.kr$")
    val email:String,

    val gender: Gender,
) {
    fun toEntity(password: String): Member =
        Member(
            memberName = memberName,
            stuNum = stuNum,
            password = password,
            email = email,
            gender = gender,
            roles = Collections.singletonList(Role.ROLE_MEMBER),
            ruleViolation = mutableListOf()
        )
}