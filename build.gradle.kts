plugins {
    kotlin("jvm") version "1.9.10"
}

group = "fr.xibalba"
version = "1.0-SNAPSHOT"

dependencies {
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(20)
}