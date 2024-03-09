plugins {
    `maven-publish`
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