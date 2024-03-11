import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    `maven-publish`
    id("org.jetbrains.dokka")
}

dependencies {
    implementation(project(":base"))

    // Hibernate
    implementation("org.hibernate.orm:hibernate-core:6.4.2.Final")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = group.toString()
            artifactId = "condition-spec-criteria-api-adapter"
            version = version
            from(components.getByName("kotlin"))
        }
    }
}