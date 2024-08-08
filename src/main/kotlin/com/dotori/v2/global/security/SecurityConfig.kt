package com.dotori.v2.global.security

import com.dotori.v2.global.error.ErrorFilter
import com.dotori.v2.global.security.handler.CustomAccessDeniedHandler
import com.dotori.v2.global.security.handler.CustomAuthenticationEntryPointHandler
import com.dotori.v2.global.security.jwt.JwtReqFilter
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
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
    private val errorFilter: ErrorFilter
) {
    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring()
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
            .antMatchers("/actuator/**").permitAll()
            .antMatchers("/v2/auth/**").permitAll()
            .antMatchers("/v2/health-check").permitAll()
            .antMatchers("/v2/refresh").permitAll()

            .antMatchers("/v2/admin/**").hasRole("ADMIN")
            .antMatchers("/v2/member/**").hasRole("MEMBER")
            .antMatchers("/v2/councillor/**").hasRole("COUNCILLOR")
            .antMatchers("/v2/developer/**").hasRole("DEVELOPER")
            .antMatchers("/v2/posting/**").hasRole("MEMBER")
            .antMatchers("/v2/student-info/**").hasAnyRole("ADMIN", "COUNCILLOR", "DEVELOPER")

            .antMatchers("/v2/email/**").permitAll()

            .antMatchers("/v2/members/**").authenticated()

            .antMatchers(HttpMethod.GET, "/v2/home/board").authenticated()
            .antMatchers(HttpMethod.GET, "/v2/home").authenticated()

            .mvcMatchers(HttpMethod.GET, "/").permitAll()

            .anyRequest().denyAll()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(CustomAccessDeniedHandler())
            .authenticationEntryPoint(CustomAuthenticationEntryPointHandler())
            .and()
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(errorFilter, JwtReqFilter::class.java)
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(12)
    }
}