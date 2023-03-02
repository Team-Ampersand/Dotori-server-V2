package com.dotori.v2.domain.music.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class MusicAlreadyException : BasicException(ErrorCode.MUSIC_ALREADY) {
}