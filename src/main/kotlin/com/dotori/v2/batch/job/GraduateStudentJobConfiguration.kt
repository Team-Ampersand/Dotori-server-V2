package com.dotori.v2.batch.job

import com.dotori.v2.batch.vaildator.PeriodJobParametersValidator
import com.dotori.v2.domain.member.domain.entity.Member
import javax.persistence.EntityManagerFactory
import org.slf4j.LoggerFactory
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GraduateStudentJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val entityManagerFactory: EntityManagerFactory
) {

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    companion object {
        const val JOB_NAME = "graduateJob"
        const val CHUNK_SIZE = 50
    }

    @Bean
    fun graduateJob(): Job = jobBuilderFactory.get(JOB_NAME)
        .start(graduateStep())
        .validator(PeriodJobParametersValidator())
        .incrementer(RunIdIncrementer())
        .build()

    @Bean
    @JobScope
    fun graduateStep(): Step {
        return stepBuilderFactory.get("graduateStep")
            .chunk<Member, Member>(CHUNK_SIZE)
            .reader(graduatePagingReader())
            .processor(graduatePagingProcessor(""))
            .writer(writer())
            .listener(object: StepExecutionListener {
                override fun beforeStep(stepExecution: StepExecution) {
                    log.info("Before Step of GraduateStep")
                }

                override fun afterStep(stepExecution: StepExecution): ExitStatus {
                    log.info("After Step of GraduateStep")

                    if(stepExecution.exitStatus.exitCode == ExitStatus.FAILED.exitCode) {
                        log.error("GraduateStep FAILED!!")
                        return ExitStatus.FAILED
                    }

                    return ExitStatus.COMPLETED
                }
            })
            .build()
    }

    @Bean
    @StepScope
    fun graduatePagingReader(): JpaPagingItemReader<Member> {
        val sqlQuery = "SELECT * FROM MEMBER m WHERE CAST(SUBSTRING(m.member_stuNum, 1, 1) AS INT) >= 3"

        val reader = object : JpaPagingItemReader<Member>() {
            override fun getPage(): Int {
                return 0
            }
        }

        reader.setQueryString(sqlQuery)
        reader.pageSize = CHUNK_SIZE
        reader.setEntityManagerFactory(entityManagerFactory)
        reader.setName("graduateReader")

        return reader
    }

    @Bean
    @StepScope
    fun graduatePagingProcessor(@Value("#{jobParameters['period']}") period: String): ItemProcessor<Member, Member> {
        return ItemProcessor { item ->
            item.graduate(period)
            item
        }
    }

    @Bean
    @StepScope
    fun writer() : ItemWriter<Member> {
        val writer = JpaItemWriter<Member>()
        writer.setEntityManagerFactory(entityManagerFactory)
        return writer
    }

}