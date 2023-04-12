package com.dotori.v2.domain.music.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class NotMyMusicException : BasicException(ErrorCode.MUSIC_NOT_MY)