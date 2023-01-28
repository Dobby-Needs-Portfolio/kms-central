plugins {
    kotlin("jvm") version "1.8.0"
}

group = "mina1316.me"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0")

    // Guava
    implementation("com.google.guava:guava:31.1-jre")

    // Log4J2
    implementation("org.apache.logging.log4j:log4j-api:2.17.1")
    implementation("org.apache.logging.log4j:log4j-core:2.17.1")
    // Log4J2 SimpleLogger

    // JavaFX
    implementation("org.openjfx:javafx:11")

    // JavaFX Coroutine support for Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.5.2")

    // Swing
    implementation("org.openjfx:javafx-swing:11")

    // ByteBuddy for Bytecode manipulation
    implementation("net.bytebuddy:byte-buddy:1.12.22")

    // H2 MVStore for Key-Value Storage
    implementation("com.h2database:h2-mvstore:2.1.214")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
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
