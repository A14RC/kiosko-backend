package com.kioskoapp.backend.controllers

import com.kioskoapp.backend.dtos.ProductRequest
import com.kioskoapp.backend.dtos.ProductResponse
import com.kioskoapp.backend.services.ProductService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

    @GetMapping
    fun getAll(): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok(productService.getAllProducts())
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ProductResponse> {
        return ResponseEntity.ok(productService.getProductById(id))
    }

    @PostMapping
    fun create(@Valid @RequestBody request: ProductRequest): ResponseEntity<ProductResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: ProductRequest
    ): ResponseEntity<ProductResponse> {
        return ResponseEntity.ok(productService.updateProduct(id, request))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }
}