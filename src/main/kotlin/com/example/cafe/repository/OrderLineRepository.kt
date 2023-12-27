package com.example.cafe.repository

import com.example.cafe.domain.entity.Product
import com.example.cafe.domain.enums.Category
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<Product, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.productId = :id")
    fun findByIdWithPessimisticLock(id: Long): Product?

    fun findByCategory(category: Category): List<Product>

    fun findTop3ByOrderBySaleCount(): List<Product>
}