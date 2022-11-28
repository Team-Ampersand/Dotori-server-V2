package com.dotori.v2.infraStructure.config.security.auth

import com.dotori.v2.domain.member.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthDetails(
    val member: Member,
) : UserDetails {

    fun getEmail(): String = member.email

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return member.roles
    }

    override fun getPassword(): String {
        return member.password
    }

    override fun getUsername(): String {
        return member.memberName
    }

    override fun isAccountNonExpired(): Boolean {
        return false
    }

    override fun isAccountNonLocked(): Boolean {
        return false
    }

    override fun isCredentialsNonExpired(): Boolean {
        return false
    }

    override fun isEnabled(): Boolean {
        return false
    }
}