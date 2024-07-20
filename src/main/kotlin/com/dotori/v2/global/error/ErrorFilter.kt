package com.dotori.v2.global.error

import com.dotori.v2.global.error.exception.BasicException
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.nio.charset.StandardCharsets

@Component
class ErrorFilter : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain) {
        runCatching {
            filterChain.doFilter(request, response)
        }.onFailure { e ->
            when (e) {
                is ExpiredJwtException -> {
                    log.error("Expired Jwt Exception Occurred - Message = {}, Status = {}", e.message, HttpStatus.UNAUTHORIZED)
                    sendError(response, ErrorResponse(ErrorCode.TOKEN_EXPIRED))
                }
                is JwtException -> {
                    log.error("Invalid Jwt Exception Occurred - Message = {}, Status = {}", e.message, HttpStatus.UNAUTHORIZED)
                    sendError(response, ErrorResponse(ErrorCode.TOKEN_INVALID))
                }
                is IllegalArgumentException -> {
                    log.error("Illegal Argument Exception Occurred - Message = {}, Status = {}", e.message, HttpStatus.BAD_REQUEST)
                    sendError(response, ErrorResponse(ErrorCode.BAD_REQUEST))
                }
                is BasicException -> {
                    log.error("Basic Exception Occurred - Message = {}, Status = {}", e.message, e.errorCode.error)
                    sendError(response, ErrorResponse(e.errorCode))
                }
                else -> {
                    log.error("Internal Exception Occurred - Message = {}, Status = {}",
                        e.message, ErrorCode.UNKNOWN_ERROR)
                    sendError(response, ErrorResponse(ErrorCode.UNKNOWN_ERROR))
                }
            }
        }
    }

    private fun sendError(response: HttpServletResponse, errorResponse: ErrorResponse) {
        val responseString = ObjectMapper().writeValueAsString(errorResponse)
        response.status = errorResponse.code
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.name()
        response.writer.write(responseString)
    }
}