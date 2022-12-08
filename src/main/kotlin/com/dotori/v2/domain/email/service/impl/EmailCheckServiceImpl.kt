package com.dotori.v2.domain.email.service.impl

import com.dotori.v2.domain.email.domain.repository.EmailCertificateRepository
import com.dotori.v2.domain.email.service.EmailCheckService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class EmailCheckServiceImpl(
    private val emailCertificateRepository: EmailCertificateRepository,
) : EmailCheckService {
    override fun execute(key: String): Boolean {
        val findByKey = emailCertificateRepository.findByKey(key) ?: throw RuntimeException()
        emailCertificateRepository.deleteByEmail(findByKey.email)
        if (!findByKey.expiredTime.isAfter(LocalDateTime.now()))
            throw RuntimeException()
        return true
    }
}
