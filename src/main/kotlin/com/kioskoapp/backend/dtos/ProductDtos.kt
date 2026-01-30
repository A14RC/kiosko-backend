package com.kioskoapp.backend.dtos

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class ProductRequest(
    @field:NotBlank(message = "El nombre es requerido")
    val name: String,

    @field:NotNull(message = "El precio es requerido")
    @field:Min(value = 0, message = "El precio no puede ser negativo")
    val price: BigDecimal,

    @field:NotNull(message = "El stock es requerido")
    @field:Min(value = 0, message = "El stock no puede ser negativo")
    val stockQuantity: Int
)

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: BigDecimal,
    val stockQuantity: Int
)