package com.dotori.v2.domain.music.presentation.developer

import com.dotori.v2.domain.music.presentation.data.req.ApplyMusicReqDto
import com.dotori.v2.domain.music.service.ApplyMusicService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/v2/developer/music")
class DeveloperMusicController(
    private val applyMusicService: ApplyMusicService
) {
    @PostMapping
    fun applyMusic(@RequestBody applyMusicReqDto: ApplyMusicReqDto): ResponseEntity<Void> =
        applyMusicService.execute(applyMusicReqDto, LocalDateTime.now().dayOfWeek)
            .run { ResponseEntity.status(HttpStatus.CREATED).build() }
}