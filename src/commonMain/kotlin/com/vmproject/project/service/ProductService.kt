// src/commonMain/kotlin/com/vmproject/project/service/ProductService.kt
package com.vmproject.project.service

import com.vmproject.project.model.Product
import io.kvision.annotations.KVService

@KVService
interface IProductService {
    suspend fun getProducts(): List<Product>
    suspend fun getProduct(id: Int): Product?
    suspend fun addProduct(product: Product): Product
    suspend fun updateProduct(product: Product): Boolean
    suspend fun deleteProduct(id: Int): Boolean
}