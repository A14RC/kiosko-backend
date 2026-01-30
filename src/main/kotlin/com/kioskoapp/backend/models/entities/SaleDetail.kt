package com.kioskoapp.backend.models.entities

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "sale_details")
data class SaleDetail(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    var sale: Sale? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product,

    @Column(name = "quantity", nullable = false)
    val quantity: Int,

    @Column(name = "subtotal", nullable = false)
    val subtotal: BigDecimal
)