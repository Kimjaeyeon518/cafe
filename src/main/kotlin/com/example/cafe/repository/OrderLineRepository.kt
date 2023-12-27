package com.example.cafe.repository

import com.example.cafe.domain.entity.OrderLine
import com.example.cafe.domain.entity.Product
import com.example.cafe.domain.enums.Category
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderLineRepository: JpaRepository<OrderLine, Long> {

}