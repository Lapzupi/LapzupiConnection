plugins {
    id("java")
    id("maven-publish")
    kotlin("jvm") version "2.1.0-RC2"
}

group = "com.lapzupi.dev.connection"
version = "1.1.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly(libs.hikari.cp)
    compileOnly(libs.flyway.core)
    compileOnly(libs.flyway.mysql)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
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


