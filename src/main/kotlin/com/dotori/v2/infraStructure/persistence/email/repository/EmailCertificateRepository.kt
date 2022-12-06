package com.dotori.v2.infraStructure.persistence.email.repository

import com.dotori.v2.domain.email.EmailCertificate
import org.springframework.data.jpa.repository.JpaRepository

interface EmailCertificateRepository: JpaRepository<EmailCertificate, Long> {
    fun deleteByEmail(email: String)
}