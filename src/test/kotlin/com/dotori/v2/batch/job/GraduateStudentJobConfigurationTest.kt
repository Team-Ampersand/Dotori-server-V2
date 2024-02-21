package com.dotori.v2.batch.job

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.*
import org.springframework.test.context.ActiveProfiles

@DisplayName("졸업 전환 배치 테스트")
@SpringBatchTest
@ActiveProfiles(value = ["dev"])
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class GraduateStudentJobConfigurationTest(
    @Autowired
    private val jobLauncherTestUtils: JobLauncherTestUtils,
    @Autowired
    private val memberRepository: MemberRepository
) {

    @Test
    @Ignore
    fun `학생_졸업_처리`(@Qualifier(value = GraduateStudentJobConfiguration.JOB_NAME) @Autowired job: Job) {
          // given
        var number = 1

        for(i in 1000L until 1010L) {
            val stuNum = "31${String.format("%02d", number)}"
            memberRepository.save(Member(id = i,
                memberName = "멤버${number}",
                stuNum = stuNum,
                email = "emailtest$number",
                password = "asdf",
                gender = Gender.MAN,
                roles = mutableListOf(Role.ROLE_MEMBER),
                ruleViolation = mutableListOf(),
                profileImage = "image"
            ))
            number++
        }

        jobLauncherTestUtils.job = job
        jobLauncherTestUtils.jobRepository = jobLauncherTestUtils.jobRepository

        val periodJobParameters = JobParametersBuilder()
            .addString("period","5기")
            .addLong("version", 35L) // date로 할까 어차피 단일 테스트고 ci돌때마다 진행하는 것도 아니라서 일단 두기 임의로 수정
            .toJobParameters()

        // when
        val jobExecution = jobLauncherTestUtils.launchJob(periodJobParameters)

        // then
//        val resultCount = memberRepository.findAllByStuNum("5기").size
//        assertThat(resultCount).isEqualTo(10)
        assertThat(jobExecution.status).isEqualTo(BatchStatus.COMPLETED)
    }

}
