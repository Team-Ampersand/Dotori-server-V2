package com.dotori.v2.domain.email.util

import com.dotori.v2.domain.email.domain.entity.EmailCertificate
import java.time.LocalDateTime

class EmailCertificateUtil {
    fun createEmailCertificate(
        id: Long = 0,
        email: String = "s00000@gsm.hs.kr",
        key: String = "0000",
        expiredTime: LocalDateTime = LocalDateTime.now(),
        authentication: Boolean = false
    ) = EmailCertificate(id, email, key, expiredTime, authentication)
}