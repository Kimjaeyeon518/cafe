package com.example.cafe.domain.dto

import com.example.cafe.domain.entity.Order
import com.example.cafe.domain.entity.OrderLine
import com.example.cafe.domain.entity.Product

data class OrderLineDto(
    var product: Product,
    var amount: Long
) {
    fun toEntity(order: Order): OrderLine {
        return OrderLine(order, product, amount)
    }
}