// src/jvmMain/kotlin/com/vmproject/project/service/ProductService.kt
package com.vmproject.project.service

import com.vmproject.project.db.Database
import com.vmproject.project.model.Product
import io.ktor.server.application.*

class ProductService(private val database: Database) : IProductService {
    override suspend fun getProducts(): List<Product> {
        return database.getAllProducts()
    }

    override suspend fun getProduct(id: Int): Product? {
        return database.getProduct(id)
    }

    override suspend fun addProduct(product: Product): Product {
        return database.addProduct(product)
    }

    override suspend fun updateProduct(product: Product): Boolean {
        return database.updateProduct(product)
    }

    override suspend fun deleteProduct(id: Int): Boolean {
        return database.deleteProduct(id)
    }
}