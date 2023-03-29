package com.dotori.v2.global.config.web

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@Profile("dev")
class DevWebConfig : WebMvcConfigurer {
    //개발용 CORS 설정
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "http://localhost:8080", "http://localhost:3000", "http://localhost:5000", "https://dotori-v2-msuj04m51-dotoriv2.vercel.app"
            )// local, dev
            .allowedMethods(
                HttpMethod.GET.name,
                HttpMethod.HEAD.name,
                HttpMethod.POST.name,
                HttpMethod.PUT.name,
                HttpMethod.DELETE.name,
                HttpMethod.PATCH.name
            )
            .maxAge(3600)
    }
}