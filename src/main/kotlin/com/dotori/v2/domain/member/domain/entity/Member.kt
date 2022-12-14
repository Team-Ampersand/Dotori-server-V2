package com.dotori.v2.domain.member.domain.entity

import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.global.entity.BaseTimeEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Member(
    @Column(name = "member_name", nullable = false)
    val memberName: String,
    
    @Column(name = "member_stuNum", nullable = false, unique = true)
    val stuNum: String,
    
    @Column(name = "member_email", nullable = false, unique = true)
    val email: String,
    
    password: String,
    
    @Enumerated(EnumType.STRING)
    @Column(name = "member_gender")
    val gender: Gender,

    @Enumerated(EnumType.STRING)
    @Column(name = "Role")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Role", joinColumns = [JoinColumn(name = "member_id")])
    val roles: MutableList<Role>,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long = 0

    @Column(name = "member_refreshToken")
    var refreshToken: String = ""
    private set

    @Column(name = "member_point", columnDefinition = "Long default 0")
    val point: Long = 0

    @Column(name = "self_study_check")
    var selfStudyCheck = false
    private set

    @Column(name = "member_password", nullable = false)
    var password: String = password
    private set

    @Column(name = "self_study_expired_date")
    var selfStudyExpiredDate: LocalDateTime? = null
    private set
}