package com.kioskoapp.backend.dtos

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDateTime

data class SaleRequest(
    @field:NotEmpty(message = "La venta debe tener al menos un producto")
    val items: List<SaleItemRequest>
)

data class SaleItemRequest(
    @field:NotNull(message = "El ID del producto es requerido")
    val productId: Long,

    @field:NotNull(message = "La cantidad es requerida")
    val quantity: Int
)

data class SaleResponse(
    val id: Long,
    val date: LocalDateTime,
    val totalAmount: BigDecimal,
    val items: List<SaleDetailResponse>
)

data class SaleDetailResponse(
    val productName: String,
    val quantity: Int,
    val pricePerUnit: BigDecimal,
    val subtotal: BigDecimal
)