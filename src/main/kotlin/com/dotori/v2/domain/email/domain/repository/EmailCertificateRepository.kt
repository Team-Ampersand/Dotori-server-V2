package com.dotori.v2.domain.email.domain.repository

import com.dotori.v2.domain.email.domain.entity.EmailCertificate
import org.springframework.data.jpa.repository.JpaRepository

interface EmailCertificateRepository: JpaRepository<EmailCertificate, Long> {
    fun deleteByEmail(email: String)

    fun findByKey(key: String): EmailCertificate?

    fun existsByEmail(email: String): Boolean
}