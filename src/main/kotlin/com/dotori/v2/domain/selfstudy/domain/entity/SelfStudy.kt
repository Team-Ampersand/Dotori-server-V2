package com.dotori.v2.domain.selfstudy.domain.entity

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.global.entity.BaseTimeEntity
import javax.persistence.*


@Entity
@Table(name = "self_Study")
class SelfStudy(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selfStudy_id")
    val id: Long = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", unique = true)
    val member: Member
) : BaseTimeEntity()