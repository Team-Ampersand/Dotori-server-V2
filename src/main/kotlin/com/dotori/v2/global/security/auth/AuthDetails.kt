package com.dotori.v2.global.security.auth

import com.dotori.v2.domain.member.domain.entity.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthDetails(
    val member: Member,
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? = member.roles

    override fun getPassword(): String? = null

    override fun getUsername(): String = member.email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}