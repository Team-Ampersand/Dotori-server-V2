package com.dotori.v2.domain.email.domain.entity

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.global.entity.BaseTimeEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "email_certificate")
class EmailCertificate(
    @Column(name = "certificate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long,
    @Column(name = "certificate_email", nullable = false)
    val email: String,
    @Column(name = "certificate_key", nullable = false)
    val key: String,
    @Column(name = "certificate_expiredTime", nullable = false)
    val expiredTime: LocalDateTime,
    @Column(name = "certificate_authentication", nullable = false)
    val authentication: Boolean
) : BaseTimeEntity() {

    constructor(email: String, key: String, expiredTime: LocalDateTime, authentication: Boolean)
            :this(0, email, key, expiredTime, authentication)

    fun verify(): EmailCertificate =
        EmailCertificate(
            id = this.id,
            email = this.email,
            key = this.key,
            expiredTime = this.expiredTime,
            authentication = true
        )
}
