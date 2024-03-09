plugins {
    `maven-publish`
}

dependencies {
    implementation(project(":base"))

    // Exposed
    val exposedVersion = "0.41.1"
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = group.toString()
            artifactId = "condition-spec-exposed-adapter"
            version = version
            from(components.getByName("kotlin"))
        }
    }
}