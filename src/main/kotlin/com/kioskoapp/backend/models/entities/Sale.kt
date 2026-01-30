package com.kioskoapp.backend.models.entities

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "sales")
data class Sale(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    val id: Long? = null,

    @Column(name = "sale_date", nullable = false)
    val saleDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "total_amount", nullable = false)
    var totalAmount: BigDecimal = BigDecimal.ZERO,

    @OneToMany(mappedBy = "sale", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var details: MutableList<SaleDetail> = mutableListOf()
)