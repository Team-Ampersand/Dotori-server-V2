package com.dotori.v2.global.security.jwt

import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtReqFilter(
    private val tokenProvider: TokenProvider,
) : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        val accessToken = tokenProvider.resolveToken(request)

        if(!accessToken.isNullOrBlank()) {

            val authentication = tokenProvider.authentication(accessToken)

            SecurityContextHolder.getContext().authentication = authentication

            log.info("current user email = ${authentication.name}")
        }

        filterChain.doFilter(request, response)
    }

}