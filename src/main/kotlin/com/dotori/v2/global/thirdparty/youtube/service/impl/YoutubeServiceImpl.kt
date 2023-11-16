package com.dotori.v2.global.thirdparty.youtube.service.impl

import com.dotori.v2.global.thirdparty.youtube.service.YoutubeService
import com.dotori.v2.global.thirdparty.youtube.data.res.YoutubeResDto
import com.dotori.v2.global.thirdparty.youtube.exception.NotValidUrlException
import com.dotori.v2.global.thirdparty.youtube.feign.YouTubeFeignClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class YoutubeServiceImpl(
    private val youtubeFeignClient: YouTubeFeignClient
) : YoutubeService {
    @Value("\${youtube.api-key}")
    private val apiKey: String = ""

    override fun getYoutubeInfo(url: String): YoutubeResDto {
        val videoId = extractVideoId(url)
        val videoInfo = youtubeFeignClient.getVideoInfo(
            part = "snippet",
            id = videoId,
            key = apiKey,
        )
        return YoutubeResDto(
            title = videoInfo.items[0].snippet.title,
            thumbnail = "https://img.youtube.com/vi/${videoId}/0.jpg"
        )
    }

    /**
     * YouTube videoId를 추출하는 로직입니다.
     */
    private fun extractVideoId(url: String): String {
        val pattern = Regex("""(?:youtu\.be/|v/|vi/|u/\w/|embed/|watch\?v(?:i)?=|&v(?:i)?=)([^#\&\?]+)""")
        return pattern.find(url)?.groupValues?.get(1) ?: throw NotValidUrlException()
    }
}