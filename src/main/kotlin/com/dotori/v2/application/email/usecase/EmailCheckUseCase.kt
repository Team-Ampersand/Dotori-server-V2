package com.dotori.v2.application.email.usecase

import com.dotori.v2.application.email.port.DeleteEmailCertificatePort
import com.dotori.v2.application.email.port.FindEmailCertificatePort
import com.dotori.v2.infraStructure.global.annotation.UseCase
import java.time.LocalDateTime

@UseCase
class EmailCheckUseCase(
    private val findEmailCertificatePort: FindEmailCertificatePort,
    private val deleteEmailCertificatePort: DeleteEmailCertificatePort,
){
    fun execute(key: String): Boolean{
        val findByKey = findEmailCertificatePort.findByKey(key)
        deleteEmailCertificatePort.delete(findByKey.email)
        if(!findByKey.expiredTime.isAfter(LocalDateTime.now()))
            throw RuntimeException()
        return true
    }
}