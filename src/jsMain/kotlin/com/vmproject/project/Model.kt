// src/jsMain/kotlin/com/vmproject/project/Model.kt
package com.vmproject.project

import com.vmproject.project.model.Product
import com.vmproject.project.service.IProductService
import io.kvision.remote.getService

object Model {
    private val pingService = getService<IPingService>()
    private val productService = getService<IProductService>()

    suspend fun ping(message: String): String {
        return pingService.ping(message)
    }

    suspend fun getProducts(): List<Product> {
        return productService.getProducts()
    }

    suspend fun getProduct(id: Int): Product? {
        return productService.getProduct(id)
    }

    suspend fun addProduct(product: Product): Product {
        return productService.addProduct(product)
    }

    suspend fun updateProduct(product: Product): Boolean {
        return productService.updateProduct(product)
    }

    suspend fun deleteProduct(id: Int): Boolean {
        return productService.deleteProduct(id)
    }
}