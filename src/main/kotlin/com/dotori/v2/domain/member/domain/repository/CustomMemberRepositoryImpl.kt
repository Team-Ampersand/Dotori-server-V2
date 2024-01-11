package com.dotori.v2.domain.member.domain.repository

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.entity.QMember.member
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.selfstudy.presentation.dto.req.SelfStudySearchReqDto
import com.dotori.v2.domain.student.presentation.data.req.SearchRequestDto
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils.hasText

@Repository
class CustomMemberRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : CustomMemberRepository {

    override fun search(searchRequestDto: SearchRequestDto): List<Member> {
        return queryFactory.selectFrom(member)
            .where(
                nameEq(searchRequestDto.name),
                gradeEq(searchRequestDto.grade),
                classNumEq(searchRequestDto.classNum),
                genderEq(searchRequestDto.gender),
                roleEq(searchRequestDto.role),
                selfStudyStatusEq(searchRequestDto.selfStudyStatus)
            )
            .orderBy(member.stuNum.asc())
            .fetch()
    }

    override fun searchSelfStudyMember(selfStudySearchReqDto: SelfStudySearchReqDto): List<Member> {
        return queryFactory.selectFrom(member)
            .where(
                nameEq(selfStudySearchReqDto.name),
                gradeEq(selfStudySearchReqDto.grade),
                classNumEq(selfStudySearchReqDto.classNum),
                genderEq(selfStudySearchReqDto.gender)
            )
            .orderBy(member.stuNum.asc())
            .fetch()
    }

    override fun existMemberByEmail(email: String): Boolean {
        val fetchOne = queryFactory
            .selectOne()
            .from(member)
            .where(member.email.eq(email))
            .fetchFirst()

        return fetchOne != null
    }

    private fun nameEq(name: String?): BooleanExpression? =
        if(hasText(name)) member.memberName.contains(name) else null

    private fun gradeEq(grade: String?): BooleanExpression? =
        if(hasText(grade)) member.stuNum.startsWith(grade) else null

    private fun classNumEq(classNum: String?): BooleanExpression? =
        if(hasText(classNum)) member.stuNum.substring(1,2).eq(classNum) else null

    private fun genderEq(gender: String?): BooleanExpression? =
        if(hasText(gender)) member.gender.eq(Gender.valueOf(gender!!)) else null

    private fun roleEq(role: String?): BooleanExpression? =
        if(hasText(role)) member.roles.any().eq(Role.valueOf(role!!)) else null

    private fun selfStudyStatusEq(selfStudyStatus: SelfStudyStatus?): BooleanExpression? =
        if(selfStudyStatus != null) member.selfStudyStatus.eq(selfStudyStatus) else null

}