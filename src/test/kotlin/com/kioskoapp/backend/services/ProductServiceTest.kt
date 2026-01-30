package com.kioskoapp.backend.services

import com.kioskoapp.backend.dtos.ProductRequest
import com.kioskoapp.backend.exceptions.ResourceNotFoundException
import com.kioskoapp.backend.models.entities.Product
import com.kioskoapp.backend.repositories.ProductRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class ProductServiceTest {

    @Mock
    lateinit var productRepository: ProductRepository

    @InjectMocks
    lateinit var productService: ProductService

    @Test
    fun `getAllProducts returns list of products`() {
        val product = Product(1L, "Cola", BigDecimal("1.50"), 10)
        Mockito.`when`(productRepository.findAll()).thenReturn(listOf(product))

        val result = productService.getAllProducts()

        Assertions.assertEquals(1, result.size)
        Assertions.assertEquals("Cola", result[0].name)
    }

    @Test
    fun `getProductById returns product when found`() {
        val product = Product(1L, "Cola", BigDecimal("1.50"), 10)
        Mockito.`when`(productRepository.findById(1L)).thenReturn(Optional.of(product))

        val result = productService.getProductById(1L)

        Assertions.assertEquals(1L, result.id)
    }

    @Test
    fun `getProductById throws exception when not found`() {
        Mockito.`when`(productRepository.findById(99L)).thenReturn(Optional.empty())

        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            productService.getProductById(99L)
        }
    }

    @Test
    fun `createProduct saves and returns product`() {
        val request = ProductRequest("Cola", BigDecimal("1.50"), 10)
        val productEntity = Product(1L, "Cola", BigDecimal("1.50"), 10)

        Mockito.`when`(productRepository.save(ArgumentMatchers.any(Product::class.java))).thenReturn(productEntity)

        val result = productService.createProduct(request)

        Assertions.assertNotNull(result.id)
        Assertions.assertEquals("Cola", result.name)
    }

    @Test
    fun `updateProduct updates and returns product`() {
        val request = ProductRequest("Cola Updated", BigDecimal("2.00"), 20)
        val existingProduct = Product(1L, "Cola", BigDecimal("1.50"), 10)

        Mockito.`when`(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct))
        Mockito.`when`(productRepository.save(ArgumentMatchers.any(Product::class.java))).thenReturn(existingProduct)

        val result = productService.updateProduct(1L, request)

        Assertions.assertEquals("Cola Updated", result.name)
        Assertions.assertEquals(BigDecimal("2.00"), result.price)
    }

    @Test
    fun `updateProduct throws exception when not found`() {
        val request = ProductRequest("Cola", BigDecimal("1.0"), 5)
        Mockito.`when`(productRepository.findById(99L)).thenReturn(Optional.empty())

        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            productService.updateProduct(99L, request)
        }
    }

    @Test
    fun `deleteProduct deletes when exists`() {
        Mockito.`when`(productRepository.existsById(1L)).thenReturn(true)
        Mockito.doNothing().`when`(productRepository).deleteById(1L)

        Assertions.assertDoesNotThrow { productService.deleteProduct(1L) }
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(1L)
    }

    @Test
    fun `deleteProduct throws exception when not exists`() {
        Mockito.`when`(productRepository.existsById(99L)).thenReturn(false)

        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            productService.deleteProduct(99L)
        }
    }
}