package com.lapzupi.dev.connection

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.FlywayException
import org.flywaydb.core.api.configuration.FluentConfiguration
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.SQLException
import java.util.concurrent.TimeUnit

/**
 * @author sarhatabaot
 */
abstract class ConnectionFactory(
    private val poolName: String,
    private val schemaHistoryName: String,
    private val baselineVersion: String
) {
    private var dataSource: HikariDataSource? = null
    private val logger by lazy { LoggerFactory.getLogger(ConnectionFactory::class.java) }


    constructor() : this(
        poolName = "hikari-connection",
        schemaHistoryName = "flyway_schema_history",
        baselineVersion = "0"
    )

    /**
     * This may be different with every database type.
     *
     * @param config       hikari config
     * @param address      address
     * @param port         port
     * @param databaseName databaseName
     * @param username     username
     * @param password     password
     */
    protected abstract fun configureDatabase(
        config: HikariConfig,
        address: String,
        port: Int,
        databaseName: String,
        username: String,
        password: String
    )

    fun init(address: String, port: Int, databaseName: String, username: String, password: String) {
        val config = HikariConfig()
        config.poolName = poolName()
        configureDatabase(config, address, port, databaseName, username, password)
        config.initializationFailTimeout = -1
        val properties: MutableMap<String, String> = HashMap()
        overrideProperties(properties)
        setProperties(config, properties)
        dataSource = HikariDataSource(config)
        logger.info("Connected to database!")

        val flyway = configureFlyway().load()
        try {
            flyway.migrate()
        } catch (e: FlywayException) {
            logger.error("There was a problem migrating to the latest database version. You may experience issues.", e)
        }
    }

    protected open fun configureFlyway(): FluentConfiguration {
        //some default settings.
        return Flyway.configure(javaClass.classLoader)
            .dataSource(dataSource)
            .baselineOnMigrate(true)
            .baselineVersion(baselineVersion())
            .table(schemaHistoryName())
    }

    protected open fun poolName() = poolName
    protected open fun schemaHistoryName() = schemaHistoryName
    protected open fun baselineVersion() = baselineVersion

    //LP
    protected open fun overrideProperties(properties: MutableMap<String, String>) {
        properties.putIfAbsent("socketTimeout", TimeUnit.SECONDS.toMillis(30).toString())
    }

    //LP
    protected fun setProperties(config: HikariConfig, properties: Map<String, String>) {
        for ((key, value) in properties) {
            config.addDataSourceProperty(key, value)
        }
    }

    fun shutdown() {
        dataSource?.close()
    }

    abstract val type: String

    @get:Throws(SQLException::class)
    val connection: Connection
        get() = requireNotNull(dataSource) { "Data source is not initialized." }.connection
}