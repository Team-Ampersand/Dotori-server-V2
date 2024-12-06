import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_BOOT_VERSION
    id("io.spring.dependency-management") version PluginVersions.SPRING_MANAGE_VERSION
    id("com.ewerk.gradle.plugins.querydsl") version PluginVersions.QUERY_DSL_PLUGIN_VERSION
    kotlin("jvm") version PluginVersions.JVM_VERSION
    kotlin("plugin.spring") version PluginVersions.SPRING_PLUG_IN_VERSION
    kotlin("plugin.jpa") version PluginVersions.JPA_VERSION
    kotlin("kapt") version PluginVersions.KAPT_VERSION
    idea
}

group = "com.dotori"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {

    // spring
    implementation(Dependencies.SPRING_JPA)
    implementation(Dependencies.SPRING_OPEN_FEIGN)
    implementation(Dependencies.SPRING_REDIS)
    implementation(Dependencies.MAIL)
    implementation(Dependencies.SECURITY)
    implementation(Dependencies.VALIDATION)
    implementation(Dependencies.WEB)
    implementation(Dependencies.SPRING_CLOUD)
    implementation(Dependencies.RETRY)

    // kotlin
    implementation(Dependencies.KOTLIN_JACKSON)
    implementation(Dependencies.KOTLIN_JACKSON_DATABIND)
    implementation(Dependencies.KOTLIN_REFLECT)
    implementation(Dependencies.KOTLIN_STDLIB)
    runtimeOnly(Dependencies.H2_DATABASE)

    // test
    testImplementation(Dependencies.SPRING_TEST)
    testImplementation(Dependencies.SECURITY_TEST)
    testImplementation(Dependencies.KOTEST_RUNNER)
    testImplementation(Dependencies.KOTEST_EXTENSION)
    testImplementation(Dependencies.KOTEST_ASSERTIONS)
    testImplementation(Dependencies.KOTEST_JVM)
    testImplementation(Dependencies.MOCKK)

    // jwt
    implementation(Dependencies.JWT_API)
    runtimeOnly(Dependencies.JWT_IMPL)
    runtimeOnly(Dependencies.JWT_JACKSON)

    // aws
    implementation(Dependencies.AWS_SES)
    implementation(Dependencies.KOTLIN_SES)
    implementation(Dependencies.AWS_MESSAGING)
    implementation(Dependencies.AWS_AUTO_CONFIG)

    // query dsl
    implementation(Dependencies.QUERY_DSL)
    implementation(Dependencies.QUERY_DSL_APT)
    kapt(Dependencies.QUERY_DSL_APT)

    // open api
    implementation(Dependencies.GOOGLE_API_CLIENT)
    implementation(Dependencies.GOOGLE_OAUTH_CLIENT)
    implementation(Dependencies.YOUTUBE_ANALYTICS)
    implementation(Dependencies.GOOGLE_API_SERVICE)

    // monitoring
    implementation(Dependencies.ACTUATOR)
    implementation(Dependencies.PROMETHEUS)

    // database
    implementation(Dependencies.MYSQL)
    runtimeOnly(Dependencies.MARIA_DATABASE)

    // batch
    implementation(Dependencies.BATCH)
    testImplementation(Dependencies.BATCH_TEST)
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    exclude("**/GraduateStudentJobConfigurationTest.class")
    useJUnitPlatform()
}

idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}


buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath(Dependencies.KOTEST_PLUG_IN)
    }
}

apply(plugin = "io.kotest")