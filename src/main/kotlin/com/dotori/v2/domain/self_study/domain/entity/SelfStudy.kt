package com.dotori.v2.domain.self_study.domain.entity

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.global.entity.BaseTimeEntity
import javax.persistence.*


@Entity
@Table(name = "SelfStudy")
class SelfStudy(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selfStudy_id")
    val id: Long = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", unique = true)
    val member: Member
) : BaseTimeEntity()