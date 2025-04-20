package com.vmproject.project.db

import com.vmproject.project.model.Product
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.Database as ExposedDatabase
import java.sql.Connection

object Products : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val description = text("description")
    val price = double("price")
    val imageUrl = varchar("image_url", 255).default("")

    override val primaryKey = PrimaryKey(id)
}

class Database(private val url: String, private val driver: String) {

    init {
        ExposedDatabase.connect(url, driver)  // Змінено на ExposedDatabase.connect
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Products)

            // Додаємо тестові дані, якщо таблиця порожня
            if (Products.selectAll().count() == 0L) {
                Products.insert {
                    it[name] = "Ноутбук"
                    it[description] = "Потужний ноутбук для роботи та ігор"
                    it[price] = 25000.0
                }
                Products.insert {
                    it[name] = "Смартфон"
                    it[description] = "Сучасний смартфон з великим екраном"
                    it[price] = 12000.0
                }
                Products.insert {
                    it[name] = "Навушники"
                    it[description] = "Бездротові навушники з шумозаглушенням"
                    it[price] = 3500.0
                }
            }
        }
    }

    fun getAllProducts(): List<Product> = transaction {
        Products.selectAll().map {
            Product(
                id = it[Products.id],
                name = it[Products.name],
                description = it[Products.description],
                price = it[Products.price],
                imageUrl = it[Products.imageUrl]
            )
        }
    }

    fun getProduct(id: Int): Product? = transaction {
        Products.select { Products.id eq id }.map {
            Product(
                id = it[Products.id],
                name = it[Products.name],
                description = it[Products.description],
                price = it[Products.price],
                imageUrl = it[Products.imageUrl]
            )
        }.singleOrNull()
    }

    fun addProduct(product: Product): Product = transaction {
        val id = Products.insert {
            it[name] = product.name
            it[description] = product.description
            it[price] = product.price
            it[imageUrl] = product.imageUrl
        } get Products.id

        product.copy(id = id)
    }

    fun updateProduct(product: Product): Boolean = transaction {
        Products.update({ Products.id eq product.id }) {
            it[name] = product.name
            it[description] = product.description
            it[price] = product.price
            it[imageUrl] = product.imageUrl
        } > 0
    }

    fun deleteProduct(id: Int): Boolean = transaction {
        Products.deleteWhere { Products.id eq id } > 0
    }
}