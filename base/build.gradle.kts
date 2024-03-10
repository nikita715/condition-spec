plugins {
    `maven-publish`
    id("org.jetbrains.dokka")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = group.toString()
            artifactId = "condition-spec-base"
            version = version
            from(components.getByName("kotlin"))
        }
    }
}