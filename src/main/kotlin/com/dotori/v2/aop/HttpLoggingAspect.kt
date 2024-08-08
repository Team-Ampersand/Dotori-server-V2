package com.dotori.v2.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.CodeSignature
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

@Aspect
@Component
class HttpLoggingAspect {

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    fun onRequest() {
    }

    @Around("onRequest()")
    @Throws(Throwable::class)
    fun logging(proceedingJoinPoint: ProceedingJoinPoint): Any? {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        val ip = request.remoteAddr
        val method = request.method
        val uri = request.requestURI
        val sessionId = request.requestedSessionId
        val params = request.queryString
        val contentType = request.contentType
        val userAgent = request.getHeader("User-Agent")
        val signature: MethodSignature = proceedingJoinPoint.signature as MethodSignature
        val className = signature.declaringType.simpleName
        val methodName = signature.name
        val headerNames = request.headerNames
        val headerSet: MutableSet<String> = HashSet()

        while (headerNames.hasMoreElements()) {
            val headerName = headerNames.nextElement()
            headerSet.add(headerName)
        }

        val code = UUID.randomUUID()
        log.info(
            "At {}#{} [Request:{}] IP: {}, Session-ID: {}, URI: {}, Params: {}, Content-Type: {}, User-Agent: {}, Headers: {}, Parameters: {}, Code: {}",
            className, methodName, method, ip, sessionId, uri, params, contentType, userAgent, headerSet, params(proceedingJoinPoint), code
        )
        val result = proceedingJoinPoint.proceed()
        when (result) {
            is ResponseEntity<*> -> {
                val body = result.body.toString().substring(0, 55)
                log.info(
                    "At {}#{} [Response:{}] IP: {}, Session-ID: {}, Headers: {}, Response: {}, Status-Code: {}, Code: {}",
                    className,methodName,method,ip,sessionId,result.headers,body,result.statusCode,code
                )
            }

            null -> {
                log.info(
                    "At {}#{} [Response: null] IP: {}, Session-ID: {}, Code: {}",
                    className, methodName, ip, sessionId, code
                )
            }

            else -> {
                throw RuntimeException("유효하지 않은 Controller 반환 타입입니다.")
            }
        }
        return result
    }

    private fun params(joinPoint: JoinPoint): Map<*,*>? {
        val codeSignature = joinPoint.signature as CodeSignature
        val parameterNames = codeSignature.parameterNames
        val args = joinPoint.args
        val params: MutableMap<String,Any> = HashMap()

        for (i in parameterNames.indices) {
            params[parameterNames[i]] = args[i]
        }
        return params
    }
}