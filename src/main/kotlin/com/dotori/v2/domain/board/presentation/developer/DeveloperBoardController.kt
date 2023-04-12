package com.dotori.v2.domain.board.presentation.developer

import com.dotori.v2.domain.board.presentation.data.req.CreateBoardReqDto
import com.dotori.v2.domain.board.presentation.data.req.DeleteMultipleBoardReqDto
import com.dotori.v2.domain.board.presentation.data.req.ModifyBoardReqDto
import com.dotori.v2.domain.board.presentation.data.res.DetailBoardResDto
import com.dotori.v2.domain.board.presentation.data.res.ListBoardResDto
import com.dotori.v2.domain.board.service.*
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid


@RestController
@RequestMapping("/v2/developer/board")
class DeveloperBoardController(
    private val createBoardService: CreateBoardService,
    private val modifyBoardService: ModifyBoardService,
    private val deleteBoardService: DeleteBoardService,
    private val deleteMultipleBoardService: DeleteMultipleBoardService,
    private val getBoardsService: GetBoardsService,
    private val getBoardDetailService: GetBoardDetailService
) {
    @PostMapping
    fun createBoard(
        @RequestPart(value = "files", required = false) multipartFiles: List<MultipartFile>?,
        @Valid @RequestPart(value = "boardDto") createBoardReqDto: CreateBoardReqDto
    ): ResponseEntity<Void> =
        createBoardService.execute(createBoardReqDto, multipartFiles)
            .run { ResponseEntity.status(HttpStatus.CREATED).build() }

    @PutMapping("/{board_id}")
    fun modifyBoard(
        @Valid @RequestBody modifyBoardReqDto: ModifyBoardReqDto,
        @PathVariable board_id: Long
    ): ResponseEntity<Void> =
        modifyBoardService.execute(modifyBoardReqDto, board_id)
            .run { ResponseEntity.status(HttpStatus.OK).build() }

    @DeleteMapping("/{board_id}")
    fun deleteBoard(@PathVariable board_id: Long): ResponseEntity<Void> =
        deleteBoardService.execute(board_id)
            .run { ResponseEntity.status(HttpStatus.OK).build() }

    @DeleteMapping()
    fun deleteMultipleBoard(@RequestBody deleteMultipleBoardReqDto: DeleteMultipleBoardReqDto): ResponseEntity<Void> =
        deleteMultipleBoardService.execute(deleteMultipleBoardReqDto)
            .run { ResponseEntity.status(HttpStatus.OK).build() }

    @GetMapping
    fun findBoards(@PageableDefault(size = 6) pageable: Pageable): ResponseEntity<ListBoardResDto> =
        ResponseEntity.status(HttpStatus.OK).body(getBoardsService.execute())

    @GetMapping("/{board_id}")
    fun findBoard(@PathVariable board_id: Long): ResponseEntity<DetailBoardResDto> =
        ResponseEntity.status(HttpStatus.OK).body(getBoardDetailService.execute(board_id))
}