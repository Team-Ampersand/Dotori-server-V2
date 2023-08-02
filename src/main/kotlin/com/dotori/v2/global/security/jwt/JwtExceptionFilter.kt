package com.dotori.v2.global.security.jwt

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.ErrorResponse
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtExceptionFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(this.javaClass.simpleName)

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            filterChain.doFilter(request, response)
        } catch (ex: ExpiredJwtException) {
            log.debug("================= [ ExceptionHandlerFilter ] 에서 ExpiredJwtException 발생 ===================")
            setErrorResponse(ErrorCode.TOKEN_EXPIRED, response)
        } catch (ex: JwtException) {
            log.debug("================= [ ExceptionHandlerFilter ] 에서 JwtException 발생 ===================")
            setErrorResponse(ErrorCode.TOKEN_INVALID, response)
        } catch (ex: IllegalArgumentException) {
            log.debug("================= [ ExceptionHandlerFilter ] 에서 JwtException 발생 ===================")
            setErrorResponse(ErrorCode.TOKEN_INVALID, response)
        } catch (ex: Exception) {
            log.debug("================= [ ExceptionHandlerFilter ] 에서 Exception 발생 ===================")
            setErrorResponse(ErrorCode.UNKNOWN_ERROR, response)
        }
    }

    @Throws(IOException::class)
    fun setErrorResponse(errorCode: ErrorCode, response: HttpServletResponse) {
        response.status = errorCode.error
        response.contentType = "application/json; charset=utf-8"
        val errorResponse = ErrorResponse(errorCode)
        val errorResponseEntityToJson = objectMapper.writeValueAsString(errorResponse)
        response.writer.write(errorResponseEntityToJson.toString())
    }
}