package com.dotori.v2.domain.music.presentation.admin

import com.dotori.v2.domain.music.presentation.data.res.MusicListResDto
import com.dotori.v2.domain.music.service.DeleteMusicService
import com.dotori.v2.domain.music.service.FindMusicsService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/v2/admin/music")
class AdminMusicController(
    private val findMusicsService: FindMusicsService,
    private val deleteMusicService: DeleteMusicService
) {
    @GetMapping
    fun findMusics(
        @RequestParam(
            value = "date",
            required = true
        ) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate
    ): ResponseEntity<MusicListResDto> =
        ResponseEntity.status(HttpStatus.OK).body(findMusicsService.execute(date))

    @DeleteMapping("/{music_id}")
    fun deleteMusic(@PathVariable music_id: Long): ResponseEntity<Void> =
        deleteMusicService.execute(music_id)
            .run { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

}