object Dependencies {

    // spring
    const val SPRING_JPA = "org.springframework.boot:spring-boot-starter-data-jpa"
    const val SPRING_REDIS = "org.springframework.boot:spring-boot-starter-data-redis"
    const val MAIL = "org.springframework.boot:spring-boot-starter-mail"
    const val SECURITY = "org.springframework.boot:spring-boot-starter-security"
    const val VALIDATION = "org.springframework.boot:spring-boot-starter-validation"
    const val WEB = "org.springframework.boot:spring-boot-starter-web"
    const val SPRING_CLOUD = "org.springframework.cloud:spring-cloud-starter-aws:${DependencyVersions.SPRING_CLOUD_VERSION}"


    // kotlin
    const val KOTLIN_JACKSON = "com.fasterxml.jackson.module:jackson-module-kotlin"
    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect"
    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"

    // h2
    const val H2_DATABASE = "com.h2database:h2"
    const val MARIA_DATABASE = "org.mariadb.jdbc:mariadb-java-client"

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
    const val KOTLIN_SES = "org.springframework.cloud:spring-cloud-starter-aws:${DependencyVersions.KOTLIN_SES_VERSION}"

    // querydsl
    const val QUERY_DSL = "com.querydsl:querydsl-jpa:${DependencyVersions.QUERY_DSL_VERSION}"
    const val QUERY_DSL_APT = "com.querydsl:querydsl-apt:${DependencyVersions.QUERY_DSL_APT_VERSION}:jpa"

}