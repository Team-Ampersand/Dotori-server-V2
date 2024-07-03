package com.dotori.v2.domain.member.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class NotAcceptImgExtensionException : BasicException(ErrorCode.MEMBER_PROFILE_IMG_NOT_ACCEPT_EXTENSION)