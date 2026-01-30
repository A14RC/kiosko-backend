package com.kioskoapp.backend.services

import com.kioskoapp.backend.dtos.SaleRequest
import com.kioskoapp.backend.dtos.SaleResponse
import com.kioskoapp.backend.exceptions.InsufficientStockException
import com.kioskoapp.backend.exceptions.ResourceNotFoundException
import com.kioskoapp.backend.mappers.toResponse
import com.kioskoapp.backend.models.entities.Sale
import com.kioskoapp.backend.models.entities.SaleDetail
import com.kioskoapp.backend.repositories.ProductRepository
import com.kioskoapp.backend.repositories.SaleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class SaleService(
    private val saleRepository: SaleRepository,
    private val productRepository: ProductRepository
) {

    @Transactional
    fun createSale(request: SaleRequest): SaleResponse {
        val sale = Sale()
        var total = BigDecimal.ZERO
        val details = mutableListOf<SaleDetail>()

        for (item in request.items) {
            val product = productRepository.findById(item.productId)
                .orElseThrow { ResourceNotFoundException("Producto no encontrado id ${item.productId}") }

            if (product.stockQuantity < item.quantity) {
                throw InsufficientStockException("Stock insuficiente para ${product.name}")
            }

            product.stockQuantity -= item.quantity
            productRepository.save(product)

            val subtotal = product.price.multiply(BigDecimal(item.quantity))
            total = total.add(subtotal)

            val detail = SaleDetail(
                sale = sale,
                product = product,
                quantity = item.quantity,
                subtotal = subtotal
            )
            details.add(detail)
        }

        sale.totalAmount = total
        sale.details = details

        val savedSale = saleRepository.save(sale)
        return savedSale.toResponse()
    }

    @Transactional(readOnly = true)
    fun getAllSales(): List<SaleResponse> {
        return saleRepository.findAll().map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    fun getSaleById(id: Long): SaleResponse {
        return saleRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Venta no encontrada id $id") }
            .toResponse()
    }
}