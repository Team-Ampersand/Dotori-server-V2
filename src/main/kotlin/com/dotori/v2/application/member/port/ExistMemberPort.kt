package com.dotori.v2.application.member.port

interface ExistMemberPort {
    fun isExist(email: String): Boolean
}