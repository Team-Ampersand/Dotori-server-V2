object Dependencies {

    // spring
    const val SPRING_JPA = "org.springframework.boot:spring-boot-starter-data-jpa"
    const val SPRING_REDIS = "org.springframework.boot:spring-boot-starter-data-redis"
    const val MAIL = "org.springframework.boot:spring-boot-starter-mail"
    const val SECURITY = "org.springframework.boot:spring-boot-starter-security"
    const val VALIDATION = "org.springframework.boot:spring-boot-starter-validation"
    const val WEB = "org.springframework.boot:spring-boot-starter-web"
    const val SPRING_CLOUD = "org.springframework.cloud:spring-cloud-starter-aws:${DependencyVersions.SPRING_CLOUD_VERSION}"
    const val SPRING_OPEN_FEIGN = "org.springframework.cloud:spring-cloud-starter-openfeign:${DependencyVersions.SPRING_CLOUD_OPEN_FEIGN}"
    const val ACTUATOR = "org.springframework.boot:spring-boot-starter-actuator"
    const val PROMETHEUS = "io.micrometer:micrometer-registry-prometheus"
    const val LOKI = "com.github.loki4j:loki-logback-appender:${DependencyVersions.LOKI}"
    const val RETRY = "org.springframework.retry:spring-retry"
    const val RESILIENCE4J = "io.github.resilience4j:resilience4j-spring-boot2:1.7.1"
    const val RATE_LIMITER = "io.github.resilience4j:resilience4j-ratelimiter:1.7.1"
    const val CIRCUIT_BREAKER = "io.github.resilience4j:resilience4j-circuitbreaker:1.7.1"

    // kotlin
    const val KOTLIN_JACKSON = "com.fasterxml.jackson.module:jackson-module-kotlin"
    const val KOTLIN_JACKSON_DATABIND = "com.fasterxml.jackson.core:jackson-databind:${DependencyVersions.JACKSON_DATA_BIND}"
    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect"
    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"

    // db
    const val H2_DATABASE = "com.h2database:h2"
    const val MARIA_DATABASE = "org.mariadb.jdbc:mariadb-java-client"
    const val MYSQL = "mysql:mysql-connector-java"

    // test
    const val SPRING_TEST = "org.springframework.boot:spring-boot-starter-test"
    const val SECURITY_TEST = "org.springframework.security:spring-security-test"

    // kotest
    const val KOTEST_PLUG_IN = "io.kotest:kotest-gradle-plugin:${PluginVersions.KOTEST_PLUG_IN_VERSION}"
    const val KOTEST_RUNNER = "io.kotest:kotest-runner-junit5:${DependencyVersions.KOTEST_RUNNER_VERSION}"
    const val KOTEST_EXTENSION = "io.kotest.extensions:kotest-extensions-spring:${DependencyVersions.KOTEST_EXTENSION_VERSION}"
    const val KOTEST_ASSERTIONS = "io.kotest:kotest-assertions-core:${DependencyVersions.KOTEST_ASSERTIONS_VERSION}"
    const val KOTEST_JVM = "io.kotest:kotest-framework-engine-jvm:${DependencyVersions.KOTEST_JVM_VERSION}"

    // mockk
    const val MOCKK = "io.mockk:mockk:${DependencyVersions.MOCKK_VERSION}"

    // jwt
    const val JWT_API = "io.jsonwebtoken:jjwt-api:${DependencyVersions.JWT_API_VERSION}"
    const val JWT_IMPL = "io.jsonwebtoken:jjwt-impl:${DependencyVersions.JWT_IMPL_VERSION}"
    const val JWT_JACKSON = "io.jsonwebtoken:jjwt-jackson:${DependencyVersions.JWT_JACKSON_VERSION}"

    // aws
    const val AWS_SES = "com.amazonaws:aws-java-sdk-ses:${DependencyVersions.AWS_SES_VERSION}"
    const val KOTLIN_SES = "org.springframework.cloud:spring-cloud-starter-aws"
    const val AWS_MESSAGING = "org.springframework.cloud:spring-cloud-aws-messaging:${DependencyVersions.AWS_MESSAGE_VERSION}"
    const val AWS_AUTO_CONFIG = "org.springframework.cloud:spring-cloud-aws-autoconfigure:${DependencyVersions.AWS_MESSAGE_VERSION}"


    // querydsl
    const val QUERY_DSL = "com.querydsl:querydsl-jpa:${DependencyVersions.QUERY_DSL_VERSION}"
    const val QUERY_DSL_APT = "com.querydsl:querydsl-apt:${DependencyVersions.QUERY_DSL_APT_VERSION}:jpa"

    // youtube
    const val GOOGLE_API_CLIENT = "com.google.api-client:google-api-client:${DependencyVersions.GOOGLE_API_CLIENT_VERSION}"
    const val GOOGLE_OAUTH_CLIENT = "com.google.oauth-client:google-oauth-client-jetty:${DependencyVersions.GOOGLE_OAUTH_VERSION}"
    const val YOUTUBE_ANALYTICS = "com.google.apis:google-api-services-youtubeAnalytics:${DependencyVersions.YOUTUBE_ANALYTICS_VERSION}"
    const val GOOGLE_API_SERVICE = "com.google.apis:google-api-services-youtube:${DependencyVersions.GOOGLE_API_SERVICE_VERSION}"

    // gauth
    const val GAUTH = "com.github.GSM-MSG:GAuth-SDK-Java:${DependencyVersions.GAUTH_VERSION}"

    // batch
    const val BATCH = "org.springframework.boot:spring-boot-starter-batch"
    const val BATCH_TEST = "org.springframework.batch:spring-batch-test"

    // http
    const val FUEL = "com.github.kittinunf.fuel:fuel:3.0.0-alpha04"
}