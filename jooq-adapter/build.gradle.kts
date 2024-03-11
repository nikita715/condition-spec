import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    `maven-publish`
    id("org.jetbrains.dokka")
}

dependencies {
    implementation(project(":base"))

    // Jooq
    implementation("org.jooq:jooq:3.18.7")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = group.toString()
            artifactId = "condition-spec-jooq-adapter"
            version = version
            from(components.getByName("kotlin"))
        }
    }
}