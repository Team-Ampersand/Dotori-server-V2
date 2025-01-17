package com.dotori.v2.domain.student.task

import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.InputStreamReader

@Component
class SyncStudentNumberTask(
    private val memberRepository: MemberRepository
) {

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    @Transactional
    fun syncStudentNumber(csv: MultipartFile) {
        log.info("========== 학생 csv 파싱 시작 ========== ")
        val parsingList = parseCsv(csv)

        log.info("========== 학생 정보 sync 시작 ========== ")
        parsingList.forEach { (email, studentNumber) ->
            val member = memberRepository.findByEmail(email)
                ?: throw MemberNotFoundException()

            if(member.stuNum == studentNumber) {
                log.info("이미 동일한 학번이 등록되어 있습니다. email: $email, studentNumber: $studentNumber")
                throw IllegalArgumentException("이미 동일한 학번이 등록되어 있습니다.")
            }

            if(member.stuNum.endsWith("기")) {
                log.info("졸업생은 학번을 싱크할 수 없습니다. email: $email, studentNumber: $studentNumber")
                throw IllegalArgumentException("졸업생은 학번을 싱크할 수 없습니다.")
            }

            if (member.stuNum == "전학") {
                log.info("전학생은 학번을 싱크할 수 없습니다. email: $email, studentNumber: $studentNumber")
                throw IllegalArgumentException("전학생은 학번을 싱크할 수 없습니다.")
            }

            member.updateStudentNumber(studentNumber)
        }

        log.info("========== 학생 정보 sync 완료 ========== ")
    }

    private fun parseCsv(file: MultipartFile): List<Pair<String, String>> {
        val reader = BufferedReader(InputStreamReader(file.inputStream))
        return reader.lines()
            .skip(1)
            .map { line ->
                val tokens = line.split("|")
                if (tokens.size != 2) {
                    throw IllegalArgumentException("Invalid CSV format")
                }
                Pair(tokens[0], tokens[1])
            }
            .toList()
    }
}