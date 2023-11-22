rootProject.name = "connection"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("hikari-cp", "com.zaxxer:HikariCP:5.1.0")
            version("flyway", "10.0.1")
            library("flyway-core", "org.flywaydb","flyway-core").versionRef("flyway")
            library("flyway-mysql", "org.flywaydb","flyway-mysql").versionRef("flyway")
        }
    }
}