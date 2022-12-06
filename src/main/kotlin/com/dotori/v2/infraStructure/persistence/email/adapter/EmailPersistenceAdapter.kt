package com.dotori.v2.infraStructure.persistence.email.adapter

import com.dotori.v2.application.email.port.EmailCertificatePort
import com.dotori.v2.domain.email.EmailCertificate
import com.dotori.v2.infraStructure.persistence.email.repository.EmailCertificateRepository
import org.springframework.stereotype.Component

@Component
class EmailPersistenceAdapter(
    private val emailCertificateRepository: EmailCertificateRepository,
): EmailCertificatePort{
    override fun delete(email: String) {
        emailCertificateRepository.deleteByEmail(email)
    }

    override fun save(emailCertificate: EmailCertificate) {
        emailCertificateRepository.save(emailCertificate)
    }
}