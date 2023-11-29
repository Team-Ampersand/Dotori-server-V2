package com.dotori.v2.testUtil

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Role

object MemberDataUtil {
    private fun email() = listOf("email", "email1" ,"email2").random()
    private fun memberName() = listOf("이름" , "이름1", "이름2").random()
    private fun memberNum() = listOf("1111", "2222", "3333").random()
    fun profileImage() = listOf("이미지", "이미지1", "이미지2").random()

    fun entity() = Member(
        memberName = memberName(),
        stuNum = memberNum(),
        email = email(),
        gender = "MALE",
        roles = mutableListOf(Role.ROLE_MEMBER),
        ruleViolation = mutableListOf(),
        profileImage = profileImage(),
    )
}