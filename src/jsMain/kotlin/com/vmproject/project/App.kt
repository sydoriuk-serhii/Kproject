// src/jsMain/kotlin/com/vmproject/project/App.kt
package com.vmproject.project

import com.vmproject.project.ui.ProductList
import io.kvision.Application
import io.kvision.CoreModule
import io.kvision.DatetimeModule
import io.kvision.TomSelectModule
import io.kvision.ToastifyModule
import io.kvision.FontAwesomeModule
import io.kvision.BootstrapIconsModule
import io.kvision.MaterialModule
import io.kvision.html.Button
import io.kvision.html.ButtonStyle
import io.kvision.html.Span
import io.kvision.i18n.DefaultI18nManager
import io.kvision.i18n.I18n
import io.kvision.module
import io.kvision.navbar.Nav
import io.kvision.navbar.Navbar
import io.kvision.navbar.navLink
import io.kvision.panel.root
import io.kvision.require
import io.kvision.startApplication
import io.kvision.toast.Toast
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
            // Створюємо навігаційне меню
            add(Navbar("Інтернет-магазин") {
                add(Nav {
                    navLink("Головна", "#")
                })
            })

            // Створюємо список товарів
            add(ProductList())
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