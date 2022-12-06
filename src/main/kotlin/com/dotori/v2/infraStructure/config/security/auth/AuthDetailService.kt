package com.dotori.v2.infraStructure.config.security.auth

import com.dotori.v2.infraStructure.persistence.member.repository.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthDetailService(
    private val memberRepository: MemberRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails =
        AuthDetails(memberRepository.findByEmail(username)
            ?: throw RuntimeException())
}