package com.dotori.v2.domain.email.domain.entity

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.global.entity.BaseTimeEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "Email_Certificate")
class EmailCertificate(
    @Column(name = "certificate_email", nullable = false)
    val email: String,
    @Column(name = "certificate_key", nullable = false)
    val key: String,
    @Column(name = "certificate_expiredTime", nullable = false)
    val expiredTime: LocalDateTime
) : BaseTimeEntity(){
    @Column(name = "certificate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long = 0
}