package com.kioskoapp.backend.repositories

import com.kioskoapp.backend.models.entities.Sale
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SaleRepository : JpaRepository<Sale, Long>