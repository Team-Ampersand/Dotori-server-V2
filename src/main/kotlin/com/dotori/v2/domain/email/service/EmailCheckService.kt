package com.dotori.v2.domain.email.service


interface EmailCheckService {
    fun execute(key: String): Boolean
}