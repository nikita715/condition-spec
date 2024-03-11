rootProject.name = "condition-spec"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

include("base")
include("exposed-adapter")
include("criteria-api-adapter")
include("exposed-adapter-example")
