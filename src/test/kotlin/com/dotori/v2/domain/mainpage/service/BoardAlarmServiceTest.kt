package com.dotori.v2.domain.mainpage.service

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.domain.repository.BoardRepository
import com.dotori.v2.domain.board.presentation.data.dto.BoardDto
import com.dotori.v2.domain.mainpage.presentation.dto.res.BoardAlarmResDto
import com.dotori.v2.domain.mainpage.service.impl.BoardAlarmServiceImpl
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.*

class BoardAlarmServiceTest : BehaviorSpec({
    val boardRepository = mockk<BoardRepository>()
    val boardAlarmServiceImpl = BoardAlarmServiceImpl(boardRepository)
    given("유저랑 작성된 게시물이 존재하고") {
        val testMember = Member(
            memberName = "test",
            stuNum = "2116",
            email = "test@gsm.hs.kr",
            password = "password1234",
            gender = Gender.MAN,
            roles = Collections.singletonList(Role.ROLE_MEMBER),
            ruleViolation = mutableListOf(),
            profileImage = null
        )
        val board = Board(1, testMember, "title", "content", boardImage = listOf())
        every { boardRepository.findAllByOrderByCreatedDateDesc() } returns listOf(board)
        `when`("서비스를 실행하면") {
            val response = boardAlarmServiceImpl.execute()
            then("해당 게시물의 내용이 반환되어야함") {
                response shouldBe BoardAlarmResDto(content = listOf(BoardDto.of(board)))
            }
        }
    }
})