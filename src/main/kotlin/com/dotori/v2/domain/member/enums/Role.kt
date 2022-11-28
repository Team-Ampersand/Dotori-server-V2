package com.dotori.v2.domain.member.enums

import org.springframework.security.core.GrantedAuthority


enum class Role : GrantedAuthority {
    ROLE_ADMIN, ROLE_MEMBER, ROLE_COUNCILLOR, ROLE_DEVELOPER;

    override fun getAuthority(): String {
        return name
    }
}