package com.dotori.v2.global.thirdparty.youtube.feign

import com.dotori.v2.global.thirdparty.youtube.data.dto.YouTubeVideoInfo
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "youtube", url = "https://www.googleapis.com/youtube/v3/videos")
@Component
interface YouTubeFeignClient {

    @GetMapping
    fun getVideoInfo(
        @RequestParam("part") part: String,
        @RequestParam("id") id: String,
        @RequestParam("key") key: String
    ): YouTubeVideoInfo

}