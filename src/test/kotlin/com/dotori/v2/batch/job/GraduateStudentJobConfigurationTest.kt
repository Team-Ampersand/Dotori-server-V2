package com.dotori.v2.batch.job

import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@TestPropertySource(properties = ["job.name=${GraduateStudentJobConfiguration.JOB_NAME}"])
internal class GraduateStudentJobConfigurationTest {

}
