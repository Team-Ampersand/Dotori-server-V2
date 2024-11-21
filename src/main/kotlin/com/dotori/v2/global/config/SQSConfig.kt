package com.dotori.v2.global.config

import com.google.api.client.util.Value
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsCredentials
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsAsyncClient

@Configuration
class SqsConfig {

    @Value("\${spring.cloud.aws.credentials.access-key:}")
    val accessKey: String? = null

    @Value("\${spring.cloud.aws.credentials.secret-key}")
    val secretKey: String? = null

    @Value("\${spring.cloud.aws.region.static}")
    val region: String? = null

    private fun credentialProvider(): AwsCredentials {
        return object : AwsCredentials {
            override fun accessKeyId(): String {
                return accessKey ?: ""
            }

            override fun secretAccessKey(): String {
                return secretKey ?: ""
            }
        }
    }

    @Bean
    fun sqsAsyncClient(): SqsAsyncClient {
        return SqsAsyncClient.builder()
            .credentialsProvider(this::credentialProvider)
            .region(Region.of(region))
            .build()
    }

    @Bean
    fun defaultSqsListenerContainerFactory(): SqsMessageListenerContainerFactory<Any> {
        return SqsMessageListenerContainerFactory.builder<Any>()
            .configure { opt ->
                opt.acknowledgementMode(AcknowledgementMode.MANUAL)
            }
            .sqsAsyncClient(sqsAsyncClient())
            .build()
    }

    @Bean
    fun sqsTemplate(): SqsTemplate {
        return SqsTemplate.newTemplate(sqsAsyncClient())
    }

}