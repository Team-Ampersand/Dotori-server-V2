package com.dotori.v2.domain.health

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {

    @GetMapping
    fun healthCheck() = ResponseEntity.ok("Dotori Server running")

}