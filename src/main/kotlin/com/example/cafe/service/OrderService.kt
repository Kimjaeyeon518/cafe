package com.example.cafe.service

import com.example.cafe.domain.entity.Order
import com.example.cafe.domain.entity.OrderLine
import com.example.cafe.domain.entity.Product
import com.example.cafe.exception.NotFoundException
import com.example.cafe.repository.OrderLineRepository
import com.example.cafe.repository.OrderRepository
import com.example.cafe.web.request.OrderLineRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class OrderService(
    val orderRepository: OrderRepository,
    val orderLineRepository: OrderLineRepository
) {

    @Transactional
    fun create(orderLines: List<OrderLineRequest>, products: List<Product>, totalPrice: Long): Order {
        val order = orderRepository.save(Order(totalPrice))
        var orderLineList = buildOrderLines(order, products, orderLines)
        orderLineRepository.saveAll(orderLineList)
        return order
    }

    fun findHit3Products(): List<Pair<Long, Long>> {
        val from = LocalDateTime.now().minusDays(7)
        val orders = orderRepository.findByCreatedAtIsAfter(from)
        var saleCountMap = mutableMapOf<Long,Long>()
        orders.map {
            order -> order.orderLines.map {
                saleCountMap.put(it.product.productId!!, saleCountMap.getOrDefault(it.product.productId, 0) + it.amount)
            }
        }
        return saleCountMap.toList()
            .sortedByDescending { it.second }
            .take(3)
    }

    fun buildOrderLines(
        order: Order,
        products: List<Product>,
        orderLines: List<OrderLineRequest>
    ): MutableList<OrderLine> {
        if (orderLines.size != products.size) {
            throw NotFoundException("유효하지 않은 상품이 존재합니다.")
        }
        var orderLineList = mutableListOf<OrderLine>()
        for (product in products) {
            for (orderLine in orderLines) {
                if (product.productId == orderLine.productId) {
                    product.purchased(orderLine.amount)
                    orderLineList.add(OrderLine(order, product, orderLine.amount))
                }
            }
        }
        return orderLineList
    }
}