package com.dotori.v2.global.thirdparty.youtube.service.impl

import com.dotori.v2.global.thirdparty.youtube.service.YoutubeService
import com.dotori.v2.global.thirdparty.youtube.data.res.YoutubeResDto
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
    private fun extractVideoId(videoUrl: String): String {
        val startIndex = videoUrl.indexOf("v=")
        if (startIndex != -1) {
            val endIndex = videoUrl.indexOfAny(charArrayOf('&', 't'), startIndex)
            return videoUrl.substring(startIndex + 2, endIndex.takeIf { it != -1 } ?: videoUrl.length)
        }
        return ""
    }
}