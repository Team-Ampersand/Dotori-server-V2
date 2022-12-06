package com.dotori.v2.application.email.port


interface DeleteEmailCertificatePort {
    fun delete(email: String)
}