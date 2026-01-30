package com.kioskoapp.backend.repositories

import com.kioskoapp.backend.models.entities.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long>