package com.dotori.v2.domain.board.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class BoardNotExistsException : BasicException(ErrorCode.BOARD_NOT_FOUND)