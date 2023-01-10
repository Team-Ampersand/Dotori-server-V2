package com.dotori.v2.global.config.security

import com.dotori.v2.global.config.security.handler.CustomAccessDeniedHandler
import com.dotori.v2.global.config.security.handler.CustomAuthenticationEntryPointHandler
import com.dotori.v2.global.config.security.jwt.JwtExceptionFilter
import com.dotori.v2.global.config.security.jwt.JwtReqFilter
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

@Component
@EnableWebSecurity
class SecurityConfig(
    private val jwtRequestFilter: JwtReqFilter,
    private val jwtExceptionFilter: JwtExceptionFilter,
) {
    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring()
                .mvcMatchers("/h2-console/**/**")
        }
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors()
            .and()
            .csrf().disable()
            .httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests()
            .mvcMatchers("/v2/auth/**").permitAll()
            .mvcMatchers("/v2/health-check").permitAll()
            .mvcMatchers("/v2/members/password/email/check").permitAll()
            .mvcMatchers("/v2/members/gender").permitAll()
            .mvcMatchers("/v2/refresh").permitAll()

            .mvcMatchers("/v2/admin/**").hasRole("ADMIN")
            .mvcMatchers("/v2/member/**").hasRole("MEMBER")
            .mvcMatchers("/v2/posting/**").hasRole("MEMBER")

            .mvcMatchers("/v2/email/**").permitAll()

            .anyRequest().denyAll()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(CustomAccessDeniedHandler())
            .authenticationEntryPoint(CustomAuthenticationEntryPointHandler())
            .and()
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(jwtExceptionFilter, JwtReqFilter::class.java)
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(12)
    }
}