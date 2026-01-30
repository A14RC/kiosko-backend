package com.kioskoapp.backend.controllers

import com.kioskoapp.backend.dtos.SaleRequest
import com.kioskoapp.backend.dtos.SaleResponse
import com.kioskoapp.backend.services.SaleService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sales")
class SaleController(private val saleService: SaleService) {

    @PostMapping
    fun createSale(@Valid @RequestBody request: SaleRequest): ResponseEntity<SaleResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.createSale(request))
    }

    @GetMapping
    fun getAllSales(): ResponseEntity<List<SaleResponse>> {
        return ResponseEntity.ok(saleService.getAllSales())
    }

    @GetMapping("/{id}")
    fun getSaleById(@PathVariable id: Long): ResponseEntity<SaleResponse> {
        return ResponseEntity.ok(saleService.getSaleById(id))
    }
}