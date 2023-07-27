package com.dotori.v2.global.security.handler

import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationEntryPointHandler : AuthenticationEntryPoint {
    private val log = LoggerFactory.getLogger(this::class.simpleName)

    @Throws(IOException::class, ServletException::class)
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
        log.info("=== AuthenticationEntryPoint ===")
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
    }
}