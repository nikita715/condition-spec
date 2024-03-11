import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    `maven-publish`
    id("org.jetbrains.dokka")
}

dependencies {
    implementation(project(":base"))

    // Hibernate
    implementation("org.hibernate.orm:hibernate-core:6.4.4.Final")

    // Tests
    testImplementation("com.h2database:h2:2.2.222")
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