package com.dotori.v2.global.util

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import com.dotori.v2.global.config.security.auth.AuthDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserUtil(
    private val userRepository: MemberRepository,
) {
    fun fetchCurrentUser(): Member {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val email = if (principal is AuthDetails) {
            principal.username
        } else {
            principal.toString()
        }
        return fetchUserByEmail(email)
    }

    fun fetchUserByEmail(email: String): Member =
        userRepository.findByEmail(email) ?: throw MemberNotFoundException()
}