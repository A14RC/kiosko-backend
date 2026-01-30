package com.kioskoapp.backend.services

import com.kioskoapp.backend.dtos.ProductRequest
import com.kioskoapp.backend.dtos.ProductResponse
import com.kioskoapp.backend.exceptions.ResourceNotFoundException
import com.kioskoapp.backend.mappers.toEntity
import com.kioskoapp.backend.mappers.toResponse
import com.kioskoapp.backend.repositories.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(private val productRepository: ProductRepository) {

    @Transactional(readOnly = true)
    fun getAllProducts(): List<ProductResponse> {
        return productRepository.findAll().map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    fun getProductById(id: Long): ProductResponse {
        val product = productRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Producto no encontrado con id $id") }
        return product.toResponse()
    }

    @Transactional
    fun createProduct(request: ProductRequest): ProductResponse {
        val product = request.toEntity()
        val savedProduct = productRepository.save(product)
        return savedProduct.toResponse()
    }

    @Transactional
    fun updateProduct(id: Long, request: ProductRequest): ProductResponse {
        val existingProduct = productRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Producto no encontrado con id $id") }

        existingProduct.name = request.name
        existingProduct.price = request.price
        existingProduct.stockQuantity = request.stockQuantity

        return productRepository.save(existingProduct).toResponse()
    }

    @Transactional
    fun deleteProduct(id: Long) {
        if (!productRepository.existsById(id)) {
            throw ResourceNotFoundException("Producto no encontrado con id $id")
        }
        productRepository.deleteById(id)
    }
}