package com.dotori.v2.global.thirdparty.youtube.service

import com.dotori.v2.global.thirdparty.youtube.data.res.YoutubeResDto

interface YoutubeService {
    fun getYoutubeInfo(url: String): YoutubeResDto
}