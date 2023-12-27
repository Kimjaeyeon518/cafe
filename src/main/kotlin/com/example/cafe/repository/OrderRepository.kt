package com.example.cafe.repository

import com.example.cafe.domain.entity.Order
import com.example.cafe.domain.entity.Product
import com.example.cafe.domain.enums.Category
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.Date

@Repository
interface OrderRepository: JpaRepository<Order, Long> {

    fun findByCreatedAtIsAfter(date: LocalDateTime): List<Order>
}