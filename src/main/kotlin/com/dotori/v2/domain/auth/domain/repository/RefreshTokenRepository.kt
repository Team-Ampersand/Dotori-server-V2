package com.dotori.v2.domain.auth.domain.repository

import com.dotori.v2.domain.auth.domain.entity.RefreshToken
import org.springframework.data.repository.CrudRepository
import java.util.*

interface RefreshTokenRepository : CrudRepository<RefreshToken, UUID> {

    fun findByMemberId(memberId: UUID): RefreshToken?

    fun findByToken(token: String): RefreshToken?
}