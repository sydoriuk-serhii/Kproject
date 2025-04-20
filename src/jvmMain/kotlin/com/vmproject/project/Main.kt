// src/jvmMain/kotlin/com/vmproject/project/Main.kt
package com.vmproject.project

import com.vmproject.project.db.Database
import com.vmproject.project.service.ProductService
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.routing.*
import io.kvision.remote.applyRoutes
import io.kvision.remote.getAllServiceManagers
import io.kvision.remote.kvisionInit
import org.koin.core.qualifier.named
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun Application.main() {
    install(Compression)
    routing {
        getAllServiceManagers().forEach { applyRoutes(it) }
    }

    val config = environment.config.config("db")
    val driver = config.property("driver").getString()
    val jdbcUrl = config.property("jdbcUrl").getString()

    val module = module {
        single { Database(jdbcUrl, driver) }
        factoryOf(::PingService)
        factoryOf(::ProductService)
    }

    kvisionInit(module)
}