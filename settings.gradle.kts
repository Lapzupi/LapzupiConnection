rootProject.name = "connection"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("flyway", "9.16.0")
            library("flyway-core", "org.flywaydb","flyway-core").versionRef("flyway")
            library("flyway-core", "org.flywaydb","flyway-mysql").versionRef("flyway")
        }
    }
}