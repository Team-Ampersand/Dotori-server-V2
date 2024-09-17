package com.dotori.v2.domain.music.presentation.member

import com.dotori.v2.domain.music.presentation.data.req.ApplyMusicReqDto
import com.dotori.v2.domain.music.presentation.data.res.MusicListResDto
import com.dotori.v2.domain.music.presentation.data.res.ToggleMusicResDto
import com.dotori.v2.domain.music.service.ApplyMusicService
import com.dotori.v2.domain.music.service.DeleteMyMusicService
import com.dotori.v2.domain.music.service.FindMusicsService
import com.dotori.v2.domain.music.service.ToggleMusicLikeService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime

@RestController
@RequestMapping("/v2/member/music")
class MemberMusicController(
    private val applyMusicService: ApplyMusicService,
    private val findMusicsService: FindMusicsService,
    private val deleteMyMusicService: DeleteMyMusicService,
    private val toggleMusicLikeService: ToggleMusicLikeService
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
        deleteMyMusicService.execute(musicId)
            .run { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

    @PatchMapping("/{music_id}/like")
    fun toggleMusicLike(@PathVariable("music_id") musicId: Long): ResponseEntity<ToggleMusicResDto> =
        ResponseEntity.status(HttpStatus.OK).body(toggleMusicLikeService.execute(musicId))
}