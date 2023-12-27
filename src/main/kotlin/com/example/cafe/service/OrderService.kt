package com.example.cafe.service

import com.example.cafe.domain.dto.OrderLineDto
import com.example.cafe.domain.entity.Order
import com.example.cafe.repository.OrderLineRepository
import com.example.cafe.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class OrderService(
    val orderRepository: OrderRepository,
    val orderLineRepository: OrderLineRepository
) {

    @Transactional
    fun create(orderLines: List<OrderLineDto>, totalPrice: Long): Order {
        val order = orderRepository.save(Order(totalPrice))
        val orderLineList = orderLines.map { it.toEntity(order) }.toList()
        orderLineRepository.saveAll(orderLineList)
        return order
    }

    fun findHit3Products(): List<Pair<Long, Long>> {
        val from = LocalDateTime.now().minusDays(7)
        val orders = orderRepository.findByCreatedAtIsAfter(from)
        var saleCountMap = mutableMapOf<Long,Long>()
        orders.map {
            order -> order.orderLines.map {
                saleCountMap.put(it.product.productId!!, saleCountMap.getOrDefault(it.product.productId, 0) + 1)
            }
        }
        return saleCountMap.toList()
            .sortedByDescending { it.second }
            .take(3)
    }
}