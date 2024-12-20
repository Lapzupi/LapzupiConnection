package com.lapzupi.dev.connection

import com.zaxxer.hikari.HikariConfig

/**
 * @author sarhatabaot
 */
class SqliteConnectionFactory(private val databaseFolder: String) : ConnectionFactory() {

    override fun configureDatabase(config: HikariConfig, address: String, port: Int, databaseName: String, username: String, password: String) {
        config.jdbcUrl = "jdbc:sqlite:plugins/$databaseFolder/$databaseName"
    }
    
    override val type: String
        get() = "SQLITE"
    
}