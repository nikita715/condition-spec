plugins {
    kotlin("jvm") version "1.9.0"
    `java-library`
    `maven-publish`
}

allprojects {
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")

    group = "io.nikstep"
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

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = group.toString()
                artifactId = rootProject.name
                version = version
                from(components["java"])
            }
        }
    }
}