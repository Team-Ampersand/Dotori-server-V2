package com.dotori.v2.application.email.port

import com.dotori.v2.domain.email.EmailCertificate

interface FindEmailCertificatePort {
    fun findByKey(key: String): EmailCertificate
}