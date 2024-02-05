package com.example.cafe.repository

import com.example.cafe.domain.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface OrderRepository: JpaRepository<Order, Long> {

    fun findByCreatedAtIsAfter(date: LocalDateTime): List<Order>
}