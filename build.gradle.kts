plugins {
    kotlin("jvm") version "1.9.0"
}

allprojects {
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "io.nikstep.condition-spec"
    version = "1.0-SNAPSHOT"

    dependencies {
        // Jitpack
        implementation("com.github.jitpack:gradle-simple:2.0")

        testImplementation(kotlin("test"))
    }

    tasks.test {
        useJUnitPlatform()
    }

    kotlin {
        jvmToolchain(8)
    }
}