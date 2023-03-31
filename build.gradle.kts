plugins {
    id("java")
    id("maven-publish")
    kotlin("jvm") version "1.8.10"
}

group = "com.lapzupi.dev.connection"
version = "1.0.1"

repositories {
    mavenCentral()
    maven(
            url = "https://repo.papermc.io/repository/maven-public/"
    )
    maven(
            url = "https://oss.sonatype.org/content/groups/public/"
    )
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly("com.zaxxer:HikariCP:5.0.1")
    compileOnly("org.flywaydb:flyway-core:9.16.0")
    compileOnly("org.flywaydb:flyway-mysql:9.16.0")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}



publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = groupId
            artifactId = artifactId
            version = version

            from(components["java"])
        }
    }
}


