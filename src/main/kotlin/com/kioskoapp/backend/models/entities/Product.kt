package com.kioskoapp.backend.models.entities

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    val id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "price", nullable = false)
    var price: BigDecimal,

    @Column(name = "stock_quantity", nullable = false)
    var stockQuantity: Int,

    @Column(nullable = false)
    var active: Boolean = true
)