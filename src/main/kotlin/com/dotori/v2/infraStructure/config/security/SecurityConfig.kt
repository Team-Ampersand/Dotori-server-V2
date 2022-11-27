package com.dotori.v2.infraStructure.config.security

import com.dotori.v2.infraStructure.config.security.handler.CustomAccessDeniedHandler
import com.dotori.v2.infraStructure.config.security.handler.CustomAuthenticationEntryPointHandler
import com.dotori.v2.infraStructure.config.security.jwt.JwtExceptionFilter
import com.dotori.v2.infraStructure.config.security.jwt.JwtReqFilter
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
            web.ignoring().antMatchers("/v1/api-docs")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/swagger-ui.html")
                .antMatchers("configuration/**")
                .antMatchers("/webjars/**")
                .antMatchers("/public")
                .and()
                .ignoring()
                .antMatchers("/h2-console/**/**")
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
            .antMatchers("/v1/signup").permitAll()
            .antMatchers("/v1/signin").permitAll()
            .antMatchers("/v1/signup/email").permitAll()
            .antMatchers("/v1/signup/email/check").permitAll()
            .antMatchers("/v1/health-check").permitAll()
            .antMatchers("/v1/members/password/email").permitAll()
            .antMatchers("/v1/members/password/email/check").permitAll()
            .antMatchers("/v1/members/gender").permitAll()
            .antMatchers("/v1/refresh").permitAll()

            .antMatchers("/v1/admin/**").hasRole("ADMIN")
            .antMatchers("/v1/member/**").hasRole("MEMBER")
            .antMatchers("/v1/posting/**").hasRole("MEMBER")

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