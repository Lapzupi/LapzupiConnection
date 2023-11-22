plugins {
    id("java")
    id("maven-publish")
    kotlin("jvm") version "1.9.20"
}

group = "com.lapzupi.dev.connection"
version = "1.0.3"

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
    compileOnly(libs.hikari.cp)
    compileOnly(libs.flyway.core)
    compileOnly(libs.flyway.mysql)
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


