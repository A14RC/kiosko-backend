package com.kioskoapp.backend.mappers

import com.kioskoapp.backend.dtos.*
import com.kioskoapp.backend.models.entities.*

fun Product.toResponse() = ProductResponse(
    id = this.id ?: 0,
    name = this.name,
    price = this.price,
    stockQuantity = this.stockQuantity
)

fun ProductRequest.toEntity() = Product(
    name = this.name,
    price = this.price,
    stockQuantity = this.stockQuantity
)

fun Sale.toResponse() = SaleResponse(
    id = this.id ?: 0,
    date = this.saleDate,
    totalAmount = this.totalAmount,
    items = this.details.map { it.toResponse() }
)

fun SaleDetail.toResponse() = SaleDetailResponse(
    productName = this.product.name,
    quantity = this.quantity,
    pricePerUnit = this.product.price,
    subtotal = this.subtotal
)