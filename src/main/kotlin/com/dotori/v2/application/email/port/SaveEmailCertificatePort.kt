package com.dotori.v2.application.email.port

import com.dotori.v2.domain.email.EmailCertificate

interface SaveEmailCertificatePort {
    fun save(emailCertificate: EmailCertificate)
}