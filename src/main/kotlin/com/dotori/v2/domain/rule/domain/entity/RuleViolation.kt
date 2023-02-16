package com.dotori.v2.domain.rule.domain.entity

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.rule.enums.Rule
import com.dotori.v2.global.entity.BaseTimeEntity
import java.time.LocalDate
import javax.persistence.*


@Entity
@Table(name = "RuleViolation")
class RuleViolation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_violation_id")
    val id: Long = 0,

    @Column(name = "rule_violation_date", nullable = false)
    val date: LocalDate,

    @Enumerated(EnumType.STRING)
    @Column(name = "rule_violation_rule", nullable = false)
    val rule: Rule,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member
) : BaseTimeEntity()