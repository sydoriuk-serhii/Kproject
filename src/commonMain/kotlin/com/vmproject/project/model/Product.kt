// src/commonMain/kotlin/com/vmproject/project/model/Product.kt
package com.vmproject.project.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int = 0,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String = ""
)