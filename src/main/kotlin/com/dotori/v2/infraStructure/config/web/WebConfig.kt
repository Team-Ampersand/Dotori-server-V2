package com.dotori.v2.infraStructure.config.web

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    //CORS 설정
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "http://localhost:8080", "http://localhost:3000", "http://localhost:5000",
                "https://www.dotori-gsm.com", "https://dotori-gsm.com", "https://server.dotori-gsm.com"
            ) // local, dotori-domain
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