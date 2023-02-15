package com.dotori.v2.global.config.security.jwt

import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.global.config.security.auth.AuthDetailService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*
import org.springframework.security.core.Authentication
import java.time.ZonedDateTime


@Component
class TokenProvider(
    private val authDetailService: AuthDetailService,
) {
    private val ACCESS_TOKEN_EXPIRE_TIME:Long = 1000 * 60 * 60 * 3// 3시간
    private val REFRESH_TOKEN_EXPIRE_TIME:Long= ACCESS_TOKEN_EXPIRE_TIME/3 * 24 * 30 * 6
    @Value("\${security.jwt.token.secretKey}")
    private val SECRET_KEY:String = ""
    val accessExpiredTime: ZonedDateTime
        get() = ZonedDateTime.now().plusSeconds(ACCESS_TOKEN_EXPIRE_TIME)


    private enum class TokenType(val value: String) {
        ACCESS_TOKEN("accessToken"),
        REFRESH_TOKEN("refreshToken")
    }

    private enum class TokenClaimName(val value: String) {
        USER_EMAIL("userEmail"),
        TOKEN_TYPE("tokenType"),
        ROLES("roles")
    }

    private fun getSignInKey(secretKey: String): Key {
        val bytes = secretKey.toByteArray(StandardCharsets.UTF_8)
        return Keys.hmacShaKeyFor(bytes)
    }

    private fun extractAllClaims(token: String): Claims {
        val tokenR = token.replace("Bearer ", "")
        return Jwts.parserBuilder()
            .setSigningKey(getSignInKey(SECRET_KEY))
            .build()
            .parseClaimsJws(tokenR)
            .body
    }

    fun getUserEmail(token: String): String = extractAllClaims(token).get(TokenClaimName.USER_EMAIL.value, String::class.java)

    fun getTokenType(token: String): String = extractAllClaims(token).get(TokenClaimName.TOKEN_TYPE.value, String::class.java)

    fun isTokenExpired(token: String): Boolean =
        try{
            extractAllClaims(token).expiration
            false
        }catch (e: ExpiredJwtException){
            true
        }

    private fun createToken(type: TokenType, email: String, expiredTime: Long, claims: Claims): String {
        val claims = Jwts.claims()
        claims[TokenClaimName.USER_EMAIL.value] = email
        claims[TokenClaimName.TOKEN_TYPE.value] = type.value
        return Jwts.builder()
            .addClaims(claims)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expiredTime))
            .signWith(getSignInKey(SECRET_KEY), SignatureAlgorithm.HS256)
            .compact()
    }

    fun createAccessToken(email: String, roles: List<Role>): String {
        val claims = Jwts.claims()
        claims[TokenClaimName.ROLES.value] = roles
        return createToken(TokenType.ACCESS_TOKEN, email, ACCESS_TOKEN_EXPIRE_TIME, claims)
    }

    fun createRefreshToken(email: String): String = createToken(TokenType.REFRESH_TOKEN, email, REFRESH_TOKEN_EXPIRE_TIME, Jwts.claims())

    fun validateToken(token: String?): Boolean = !isTokenExpired(token!!)

    fun getAuthentication(token: String?): Authentication {
        val userDetails: UserDetails = authDetailService.loadUserByUsername(getUserEmail(token!!))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun isRefreshToken(token: String): Boolean =
        getTokenType(token) == TokenType.REFRESH_TOKEN.value
}