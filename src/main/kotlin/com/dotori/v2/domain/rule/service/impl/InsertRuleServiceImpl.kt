package com.dotori.v2.domain.rule.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import com.dotori.v2.domain.rule.domain.entity.RuleViolation
import com.dotori.v2.domain.rule.domain.repository.RuleRepository
import com.dotori.v2.domain.rule.enums.Rule
import com.dotori.v2.domain.rule.presentation.data.dto.RuleGrantDto
import com.dotori.v2.domain.rule.presentation.data.req.RuleGrantReqDto
import com.dotori.v2.domain.rule.service.InsertRuleService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(rollbackFor = [Exception::class])
class InsertRuleServiceImpl(
    private val ruleRepository: RuleRepository,
    private val memberRepository: MemberRepository
) : InsertRuleService {
    override fun execute(ruleGrantReqDto: RuleGrantReqDto) {
        val ruleGrantDto: RuleGrantDto = ruleGrantReqDto.toDto()
        ruleGrantDto.stuNum.forEach {
            val member: Member = memberRepository.findByStuNum(stuNum = it) ?: throw MemberNotFoundException()
            ruleRepository.saveAll(ruleGrantDto.rule
                .map { rule ->
                    toEntity(
                        member = member,
                        rule = rule,
                        date = ruleGrantDto.date
                    )
                })
        }
    }

    private fun RuleGrantReqDto.toDto(): RuleGrantDto =
        RuleGrantDto(
            stuNum = this.stuNum,
            rule = this.rule,
            date = this.date
        )

    private fun toEntity(member: Member, rule: Rule, date: LocalDate): RuleViolation =
        RuleViolation(
            member = member,
            rule = rule,
            date = date
        )
}