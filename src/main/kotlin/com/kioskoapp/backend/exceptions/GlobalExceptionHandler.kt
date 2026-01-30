package com.kioskoapp.backend.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFound(ex: ResourceNotFoundException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to (ex.message ?: "Not found")))
    }

    @ExceptionHandler(InsufficientStockException::class)
    fun handleStock(ex: InsufficientStockException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to (ex.message ?: "Stock error")))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        val errors = ex.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: "Invalid") }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors)
    }
}

class ResourceNotFoundException(message: String) : RuntimeException(message)
class InsufficientStockException(message: String) : RuntimeException(message)