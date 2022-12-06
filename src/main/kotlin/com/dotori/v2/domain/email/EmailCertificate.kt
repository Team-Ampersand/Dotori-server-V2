package com.dotori.v2.domain.email

import com.dotori.v2.domain.member.Member
import com.dotori.v2.infraStructure.entity.BaseTimeEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "Email_Certificate")
class EmailCertificate(
    @Column(name = "certificate_email", nullable = false)
    val email: String,
    @field:Column(name = "certificate_key", nullable = false)
    val key: String,
    @field:Column(name = "certificate_expiredTime", nullable = false)
    val expiredTime: LocalDateTime
) : BaseTimeEntity(){
    @Column(name = "certificate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long = 0
}