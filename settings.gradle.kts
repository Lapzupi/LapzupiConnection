rootProject.name = "connection"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("hikari-cp", "com.zaxxer:HikariCP:6.2.1")
            version("flyway", "11.0.0")
            library("flyway-core", "org.flywaydb","flyway-core").versionRef("flyway")
            library("flyway-mysql", "org.flywaydb","flyway-mysql").versionRef("flyway")
        }
    }
}