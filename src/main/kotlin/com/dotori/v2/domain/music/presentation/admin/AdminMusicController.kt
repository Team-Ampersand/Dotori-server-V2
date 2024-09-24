package com.dotori.v2.domain.music.presentation.admin

import com.dotori.v2.domain.music.presentation.data.res.MusicLikeCountResDto
import com.dotori.v2.domain.music.presentation.data.res.MusicListResDto
import com.dotori.v2.domain.music.presentation.data.res.MusicRankListResDto
import com.dotori.v2.domain.music.service.DeleteMusicService
import com.dotori.v2.domain.music.service.FindMusicRankService
import com.dotori.v2.domain.music.service.FindMusicsService
import com.dotori.v2.domain.music.service.ToggleMusicLikeService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/v2/admin/music")
class AdminMusicController(
    private val findMusicsService: FindMusicsService,
    private val deleteMusicService: DeleteMusicService,
    private val toggleMusicLikeService: ToggleMusicLikeService,
    private val findMusicRankService: FindMusicRankService
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
    fun deleteMusic(@PathVariable("music_id") musicId: Long): ResponseEntity<Void> =
        deleteMusicService.execute(musicId)
            .run { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

    @PatchMapping("/{music_id}/like")
    fun toggleMusicLike(@PathVariable("music_id") musicId: Long): ResponseEntity<MusicLikeCountResDto> =
        ResponseEntity.status(HttpStatus.OK).body(toggleMusicLikeService.execute(musicId))

    @GetMapping("/like")
    fun findMusicRank(
        @RequestParam(
            value = "date",
            required = true
        ) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate
    ): ResponseEntity<MusicRankListResDto> =
        ResponseEntity.status(HttpStatus.OK).body(findMusicRankService.execute(date))
}