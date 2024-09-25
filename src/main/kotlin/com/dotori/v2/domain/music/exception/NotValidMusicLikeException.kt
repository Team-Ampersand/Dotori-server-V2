package com.dotori.v2.domain.music.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class NotValidMusicLikeException : BasicException(ErrorCode.NOT_VALID_MUSIC_LIKE) {
}