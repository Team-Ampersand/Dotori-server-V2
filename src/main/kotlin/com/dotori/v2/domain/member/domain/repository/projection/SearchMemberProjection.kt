package com.dotori.v2.domain.member.domain.repository.projection

import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.student.presentation.data.res.SearchStudentListResDto
import com.querydsl.core.annotations.QueryProjection

data class SearchMemberProjection @QueryProjection constructor (
    val id: Long,
    val name: String,
    val stuNum: String,
    val gender: Gender,
    val role: Role,
    val selfStudyStatus: SelfStudyStatus,
    val profileImage: String?,
    val email: String?
){
    fun toDto(): SearchStudentListResDto{
        return SearchStudentListResDto(
            id = this.id,
            memberName = this.name,
            stuNum = this.stuNum,
            gender = this.gender,
            role = this.role,
            selfStudyStatus = this.selfStudyStatus,
            profileImage = this.profileImage ?: "",
            email = this.email ?: ""
        )
    }
}