// Kotlin-based Spring boot project
plugins {
    kotlin("jvm") version "1.8.0"
}

group = "mina1316.me"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0")

    // Spring

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