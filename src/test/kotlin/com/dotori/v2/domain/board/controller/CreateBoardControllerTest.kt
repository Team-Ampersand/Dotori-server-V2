package com.dotori.v2.domain.board.controller

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.presentation.admin.AdminBoardController
import com.dotori.v2.domain.board.presentation.councillor.CouncillorBoardController
import com.dotori.v2.domain.board.presentation.data.req.CreateBoardReqDto
import com.dotori.v2.domain.board.presentation.developer.DeveloperBoardController
import com.dotori.v2.domain.board.service.CreateBoardService
import com.dotori.v2.domain.board.service.DeleteBoardService
import com.dotori.v2.domain.board.service.GetBoardsService
import com.dotori.v2.domain.board.service.ModifyBoardService
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus
import org.springframework.web.multipart.MultipartFile
import java.util.*

class CreateBoardControllerTest : BehaviorSpec({
    val createBoardService = mockk<CreateBoardService>()
    val modifyBoardService = mockk<ModifyBoardService>()
    val deleteBoardService = mockk<DeleteBoardService>()
    val getBoardsService = mockk<GetBoardsService>()

    val councillorCreateBoardController =
        CouncillorBoardController(
            createBoardService = createBoardService,
            modifyBoardService = modifyBoardService,
            deleteBoardService = deleteBoardService,
            getBoardsService = getBoardsService
        )
    val developerCreateBoardController =
        DeveloperBoardController(
            createBoardService = createBoardService,
            modifyBoardService = modifyBoardService,
            deleteBoardService = deleteBoardService,
            getBoardsService = getBoardsService
        )
    val adminCreateBoardController =
        AdminBoardController(
            createBoardService = createBoardService,
            modifyBoardService = modifyBoardService,
            deleteBoardService = deleteBoardService,
            getBoardsService = getBoardsService
        )

    given("요청이 들어오면") {
        val createBoardReqDto = CreateBoardReqDto(
            title = "thisIsTitle",
            content = "thisIsContent"
        )
        val multipartFiles = listOf<MultipartFile>()
        val member = Member(
            memberName = "test",
            stuNum = "2116",
            email = "test@gsm.hs.kr",
            password = "test",
            gender = Gender.MAN,
            roles = Collections.singletonList(Role.ROLE_MEMBER)
        )
        val board = Board(
            title = "thisIsTitle",
            content = "thisIsContent",
            member = member
        )
        every { createBoardService.execute(createBoardReqDto, multipartFiles) } returns board
        `when`("councillorController is received") {
            val response = councillorCreateBoardController.createBoard(multipartFiles, createBoardReqDto)
            then("service 가 한번은 실행되어야 함") {
                verify(exactly = 1) { createBoardService.execute(createBoardReqDto, multipartFiles) }
            }
            then("response status should be created") {
                response.statusCode shouldBe HttpStatus.CREATED
            }
        }
        `when`("developerController is received") {
            val response = developerCreateBoardController.createBoard(multipartFiles, createBoardReqDto)
            then("service 가 한번은 실행되어야 함") {
                verify(exactly = 2) { createBoardService.execute(createBoardReqDto, multipartFiles) }
            }
            then("response status should be created") {
                response.statusCode shouldBe HttpStatus.CREATED
            }
        }
        `when`("adminController is received") {
            val response = adminCreateBoardController.createBoard(multipartFiles, createBoardReqDto)
            then("service 가 한번은 실행되어야 함") {
                verify(exactly = 3) { createBoardService.execute(createBoardReqDto, multipartFiles) }
            }
            then("response status should be created") {
                response.statusCode shouldBe HttpStatus.CREATED
            }
        }
    }
})