package com.dotori.v2.domain.music.presentation.developer

import com.dotori.v2.domain.music.presentation.data.req.ApplyMusicReqDto
import com.dotori.v2.domain.music.presentation.data.res.MusicListResDto
import com.dotori.v2.domain.music.service.ApplyMusicService
import com.dotori.v2.domain.music.service.DeleteMusicLikeService
import com.dotori.v2.domain.music.service.DeleteMusicService
import com.dotori.v2.domain.music.service.FindMusicsService
import com.dotori.v2.domain.music.service.PostMusicLikeService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime

@RestController
@RequestMapping("/v2/developer/music")
class DeveloperMusicController(
    private val applyMusicService: ApplyMusicService,
    private val findMusicsService: FindMusicsService,
    private val deleteMusicService: DeleteMusicService,
    private val postMusicLikeService: PostMusicLikeService,
    private val deleteMusicLikeService: DeleteMusicLikeService,
) {
    @PostMapping
    fun applyMusic(@RequestBody applyMusicReqDto: ApplyMusicReqDto): ResponseEntity<Void> =
        applyMusicService.execute(applyMusicReqDto, LocalDateTime.now().dayOfWeek)
            .run { ResponseEntity.status(HttpStatus.CREATED).build() }

    @GetMapping
    fun findMusics(
        @RequestParam(
            value = "date",
            required = true
        ) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate
    ): ResponseEntity<MusicListResDto> =
        ResponseEntity.status(HttpStatus.OK).body(findMusicsService.execute(date))

    @DeleteMapping("/{music_id}")
    fun deleteMusic(@PathVariable("music_id") musicId: Long): ResponseEntity<Void> =
        deleteMusicService.execute(musicId)
            .run { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

    @PostMapping("/{music_id}/like")
    fun postMusicLike(@PathVariable("music_id") musicId: Long): ResponseEntity<Void> =
        postMusicLikeService.execute(musicId)
            .run { ResponseEntity.status(HttpStatus.OK).build() }

    @DeleteMapping("/{music_id}/like")
    fun deleteMusicLike(@PathVariable("music_id") musicId: Long): ResponseEntity<Void> =
        deleteMusicLikeService.execute(musicId)
            .run { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }
}