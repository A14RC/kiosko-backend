package com.kioskoapp.backend.services

import com.kioskoapp.backend.dtos.SaleItemRequest
import com.kioskoapp.backend.dtos.SaleRequest
import com.kioskoapp.backend.exceptions.InsufficientStockException
import com.kioskoapp.backend.exceptions.ResourceNotFoundException
import com.kioskoapp.backend.models.entities.Product
import com.kioskoapp.backend.models.entities.Sale
import com.kioskoapp.backend.repositories.ProductRepository
import com.kioskoapp.backend.repositories.SaleRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.util.*

@ExtendWith(MockitoExtension::class)
class SaleServiceTest {

    @Mock
    lateinit var saleRepository: SaleRepository

    @Mock
    lateinit var productRepository: ProductRepository

    @InjectMocks
    lateinit var saleService: SaleService

    @Test
    fun `createSale creates sale successfully`() {
        val itemReq = SaleItemRequest(1L, 2)
        val request = SaleRequest(listOf(itemReq))

        val product = Product(1L, "Cola", BigDecimal("1.50"), 10)
        val saleSaved = Sale(1L, totalAmount = BigDecimal("3.00"))

        `when`(productRepository.findById(1L)).thenReturn(Optional.of(product))
        `when`(productRepository.save(any(Product::class.java))).thenReturn(product)
        `when`(saleRepository.save(any(Sale::class.java))).thenReturn(saleSaved)

        val result = saleService.createSale(request)

        assertNotNull(result)
        assertEquals(BigDecimal("3.00"), result.totalAmount)
        verify(productRepository, times(1)).save(product) // Verifica que se redujo el stock
    }

    @Test
    fun `createSale throws InsufficientStockException`() {
        val itemReq = SaleItemRequest(1L, 20) // Pide 20
        val request = SaleRequest(listOf(itemReq))
        val product = Product(1L, "Cola", BigDecimal("1.50"), 10) // Solo hay 10

        `when`(productRepository.findById(1L)).thenReturn(Optional.of(product))

        assertThrows(InsufficientStockException::class.java) {
            saleService.createSale(request)
        }
    }

    @Test
    fun `createSale throws ResourceNotFoundException when product not found`() {
        val itemReq = SaleItemRequest(99L, 1)
        val request = SaleRequest(listOf(itemReq))

        `when`(productRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows(ResourceNotFoundException::class.java) {
            saleService.createSale(request)
        }
    }

    @Test
    fun `getAllSales returns list`() {
        `when`(saleRepository.findAll()).thenReturn(listOf(Sale()))
        val result = saleService.getAllSales()
        assertEquals(1, result.size)
    }

    @Test
    fun `getSaleById returns sale`() {
        val sale = Sale(1L)
        `when`(saleRepository.findById(1L)).thenReturn(Optional.of(sale))
        val result = saleService.getSaleById(1L)
        assertEquals(1L, result.id)
    }

    @Test
    fun `getSaleById throws exception when not found`() {
        `when`(saleRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(ResourceNotFoundException::class.java) {
            saleService.getSaleById(99L)
        }
    }
}