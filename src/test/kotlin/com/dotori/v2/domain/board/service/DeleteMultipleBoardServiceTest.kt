package com.dotori.v2.domain.board.service

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.domain.entity.BoardImage
import com.dotori.v2.domain.board.domain.repository.BoardImageRepository
import com.dotori.v2.domain.board.domain.repository.BoardRepository
import com.dotori.v2.domain.board.presentation.data.req.DeleteMultipleBoardReqDto
import com.dotori.v2.domain.board.service.impl.DeleteMultipleBoardServiceImpl
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.global.thirdparty.aws.s3.S3Service
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.data.repository.findByIdOrNull
import java.util.*

class DeleteMultipleBoardServiceTest : BehaviorSpec({
    val boardRepository = mockk<BoardRepository>()
    val boardImageRepository = mockk<BoardImageRepository>()
    val s3Service = mockk<S3Service>()

    val deleteMultipleBoardServiceImpl = DeleteMultipleBoardServiceImpl(boardRepository, boardImageRepository, s3Service)

    Given("공지사항 과 공지사항안에 이미자가 주어지면") {
        val testMember = Member(
            memberName = "test",
            stuNum = "2116",
            email = "test@gsm.hs.kr",
            password = "test",
            gender = Gender.MAN,
            roles = Collections.singletonList(Role.ROLE_MEMBER),
            ruleViolation = mutableListOf()
        )
        val board = Board(
            id = 0,
            member =testMember,
            title = "공지사항 테스트",
            content = "공지사항 테스트 입니다.",
            boardImage = mutableListOf()
        )
        val boardImage1 = mutableListOf(BoardImage(0, board, "https://s3.amazondawdwadwadaddawdadawdawdwadadadadwad/image.jpeg"))
        val deleteMultipleBoardReqDto = DeleteMultipleBoardReqDto(mutableListOf(board.id))

        every { boardRepository.findByIdOrNull(board.id) } returns board
        every { boardImageRepository.findAllByBoard_Id(board.id) } returns boardImage1
        every { boardRepository.delete(board) } returns Unit
        every { boardImageRepository.delete(boardImage1[0]) } returns Unit
        every { s3Service.deleteFile(boardImage1[0].url.substring(54)) } returns Unit

        When("서비스를 실행하면") {
            deleteMultipleBoardServiceImpl.execute(deleteMultipleBoardReqDto)

            Then("boardRepository.delete 메서드가 실행되어야함") {
                verify { boardRepository.delete(board) }
            }
            Then("boardImageRepository.delete 메서드가 실행되어야함") {
                verify { boardImageRepository.delete(boardImage1[0]) }
            }
        }
    }
})