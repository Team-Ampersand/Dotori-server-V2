package com.dotori.v2.batch.job

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
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
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.data.RepositoryItemWriter
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GraduateStudentJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val entityManagerFactory: EntityManagerFactory,
    private val memberRepository: MemberRepository
) {

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    companion object {
        const val JOB_NAME = "graduateJob"
        const val CHUNK_SIZE = 10
    }

//    @Bean
    fun graduateJob(): Job = jobBuilderFactory.get(JOB_NAME)
        .start(graduateStep())
//        .incrementer(UniqueRunIdIncrementer())
//        .validator(PeriodJobParametersValidator())
        .build()

    @Bean
    @JobScope
    fun graduateStep(): Step {
        return stepBuilderFactory.get("graduateStep")
            .chunk<Member, Member>(CHUNK_SIZE)
            .reader(graduatePagingReader())
            .processor(graduatePagingProcessor(null, null))
            .writer(writer())
            .listener(object: StepExecutionListener {
                override fun beforeStep(stepExecution: StepExecution) {
                    log.info("==========Before Step of GraduateStep==========")
                }

                override fun afterStep(stepExecution: StepExecution): ExitStatus {
                    log.info("==========After Step of GraduateStep==========")

                    if(stepExecution.exitStatus.exitCode == ExitStatus.FAILED.exitCode) {
                        log.error("==========GraduateStep FAILED!!!===========")
                        return ExitStatus.FAILED
                    }
                    log.info("==========GraduateStep COMPLETED:)===========")
                    return ExitStatus.COMPLETED
                }
            })
            .build()
    }

    @Bean
    @StepScope
    fun graduatePagingReader(): JpaPagingItemReader<Member> {

        val sqlQuery = "SELECT m FROM Member m " +
                "WHERE LENGTH(m.stuNum) >= 4 AND " +
                "m.stuNum >= 3101"

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
    fun graduatePagingProcessor(
        @Value("#{jobParameters['period']}") period: String?,
        @Value("#{jobParameters['version']}") version: Long?
    ): ItemProcessor<Member, Member> = ItemProcessor { it.graduate(period!!) }

    @Bean
    @StepScope
    fun writer() : RepositoryItemWriter<Member> = RepositoryItemWriterBuilder<Member>()
        .repository(memberRepository)
        .methodName("save")
        .build()
}
