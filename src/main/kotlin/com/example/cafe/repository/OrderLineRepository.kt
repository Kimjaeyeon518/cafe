package com.example.cafe.repository

import com.example.cafe.domain.entity.OrderLine
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderLineRepository: JpaRepository<OrderLine, Long> {

}