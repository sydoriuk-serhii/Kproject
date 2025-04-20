// src/jsMain/kotlin/com/vmproject/project/ui/ProductList.kt
package com.vmproject.project.ui

import com.vmproject.project.AppScope
import com.vmproject.project.model.Product
import com.vmproject.project.service.IProductService
import io.kvision.core.*
import io.kvision.html.*
import io.kvision.panel.FlexPanel
import io.kvision.panel.VPanel
import io.kvision.panel.vPanel
import io.kvision.remote.getService
import io.kvision.toast.Toast
import io.kvision.utils.px
import kotlinx.coroutines.launch

// Змінено наслідування з Container на FlexPanel()
class ProductList : FlexPanel() {
    private val productService = getService<IProductService>()

    init {
        // Налаштовуємо властивості FlexPanel безпосередньо
        flexWrap = FlexWrap.WRAP // Кращий спосіб встановлення flex-wrap
        padding = 20.px

        // Викликаємо завантаження продуктів безпосередньо
        loadProducts()
    }

    // Функція тепер є звичайним методом класу, а не розширенням
    private fun loadProducts() {
        AppScope.launch {
            try {
                val products = productService.getProducts()
                if (products.isEmpty()) {
                    // Додаємо елементи безпосередньо до 'this' (ProductList)
                    add(Div("Немає доступних товарів"))
                } else {
                    products.forEach { product ->
                        // Додаємо елементи безпосередньо до 'this'
                        add(createProductCard(product))
                    }
                }
            } catch (e: Exception) {
                // Обробка помилок залишається такою ж
                Toast.warning("Помилка завантаження товарів: ${e.message}")
            }
        }
    }

    // Змінено тип повернення на VPanel для більшої конкретики
    private fun createProductCard(product: Product): VPanel {
        return vPanel {
            // Використання KVision DSL для стилів замість setAttribute
            border = Border(1.px, BorderStyle.SOLID, Color.name(Col.LIGHTGRAY))
            borderRadius = 8.px
            padding = 16.px
            width = 300.px // Розгляньте використання відносних одиниць для адаптивності

            // Вміст картки залишається без змін
            add(H4(product.name))
            add(P(product.description))
            add(P("Ціна: ${product.price} грн"))

            add(Button("Додати до кошика", style = ButtonStyle.PRIMARY).apply {
                onClick {
                    Toast.success("Товар додано до кошика")
                    // Тут можна додати логіку додавання до кошика
                }
            })
        }
    }
}