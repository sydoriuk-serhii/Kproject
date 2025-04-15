package com.vmproject.project

import io.kvision.Application
import io.kvision.CoreModule
import io.kvision.DatetimeModule
import io.kvision.TomSelectModule
import io.kvision.ToastifyModule
import io.kvision.FontAwesomeModule
import io.kvision.BootstrapIconsModule
import io.kvision.MaterialModule
import io.kvision.html.Span
import io.kvision.i18n.DefaultI18nManager
import io.kvision.i18n.I18n
import io.kvision.module
import io.kvision.panel.root
import io.kvision.require
import io.kvision.startApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

val AppScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

class App : Application() {

    override fun start(state: Map<String, Any>) {
        I18n.manager =
            DefaultI18nManager(
                mapOf(
                    "en" to require("i18n/messages-en.json"),
                    "pl" to require("i18n/messages-pl.json")
                )
            )

        val root = root("kvapp") {
        }
        AppScope.launch {
            val pingResult = Model.ping("Hello world from client!")
            root.add(Span(pingResult))
        }
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        DatetimeModule,
        TomSelectModule,
        ToastifyModule,
        FontAwesomeModule,
        BootstrapIconsModule,
        MaterialModule,
        CoreModule
    )
}
