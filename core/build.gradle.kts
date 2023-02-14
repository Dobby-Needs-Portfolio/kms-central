// Kotlin-based Spring boot project
plugins {
    kotlin("jvm") version "1.8.0" // Kotlin
    kotlin("plugin.spring") version "1.8.0" // Kotlin Spring support plugin
//    id("org.gradle.kotlin.kotlin-dsl") version "4.0.7" // Kotlin DSL for Gradle
    id("io.spring.dependency-management") version "1.1.0" // Spring Boot Dependency Management
    id("org.springframework.boot") version "2.7.8" // Spring Boot
}


group = "mina1316.me"
version = "prerelease-0.1.0 alpha"

repositories {
    mavenCentral()
}

configurations.all {
    // Remove default springboot's logger dependency
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    // Remove default springboot's tomcat dependency
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
}
// Application dependencies management
dependencyManagement {
    val springCloudVersion = "2021.0.5"
    val springBootAdminVersion = "2.7.10"
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
        mavenBom("de.codecentric:spring-boot-admin-dependencies:${springBootAdminVersion}")
    }
}

// Application dependencies
dependencies {

    // ==== Kotlin ====
    // Reflection
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.0")
    // Standard library
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // ==== Spring Boot ====
    // Starter
    implementation("org.springframework.boot:spring-boot-starter")
    // Jetty for Web server
    implementation("org.springframework.boot:spring-boot-starter-jetty")
    // Spring boot Actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    // Spring boot Admin page
    implementation("de.codecentric:spring-boot-admin-starter-server")
    // Spring boot admin server Login
    implementation("de.codecentric:spring-boot-admin-server-ui")
    // Spring boot admin client
    implementation("de.codecentric:spring-boot-admin-starter-client")
    // Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    // Spring boot autoconfigure
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    // Spring boot Devtools
    implementation("org.springframework.boot:spring-boot-devtools")

    // ==== Spring Cloud ====
    // Spring cloud Eureka service registration
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")

    // ==== Logging/Error Collection ====
    // Log4J2
    implementation("org.apache.logging.log4j:log4j-api:2.17.1")
    implementation("org.apache.logging.log4j:log4j-core:2.17.1")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.17.1")
    // Sentry
    implementation("io.sentry:sentry:6.13.1")
    implementation("io.sentry:sentry-log4j2:5.3.0")

}

// Test dependencies
dependencies {

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }

    jvmToolchain(11)
}

sourceSets.main {
    java.srcDirs("src/main/java", "src/main/kotlin")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}