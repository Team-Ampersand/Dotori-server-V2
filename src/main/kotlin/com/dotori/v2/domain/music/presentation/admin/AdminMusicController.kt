package com.dotori.v2.domain.music.presentation.admin

import com.dotori.v2.domain.music.presentation.data.res.MusicListResDto
import com.dotori.v2.domain.music.service.DeleteMusicLikeService
import com.dotori.v2.domain.music.service.DeleteMusicService
import com.dotori.v2.domain.music.service.FindMusicsService
import com.dotori.v2.domain.music.service.PostMusicLikeService
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
    private val postMusicLikeService: PostMusicLikeService,
    private val deleteMusicLikeService: DeleteMusicLikeService,
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

    @PostMapping("/{music_id}/like")
    fun postMusicLike(@PathVariable("music_id") musicId: Long): ResponseEntity<Void> =
        postMusicLikeService.execute(musicId)
            .run { ResponseEntity.status(HttpStatus.OK).build() }

    @DeleteMapping("/{music_id}/like")
    fun deleteMusicLike(@PathVariable("music_id") musicId: Long): ResponseEntity<Void> =
        deleteMusicLikeService.execute(musicId)
            .run { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

}