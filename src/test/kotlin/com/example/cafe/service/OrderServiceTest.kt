package com.example.cafe.service

import com.example.cafe.domain.entity.Order
import com.example.cafe.domain.entity.Product
import com.example.cafe.domain.enums.Category
import com.example.cafe.repository.OrderLineRepository
import com.example.cafe.repository.OrderRepository
import com.example.cafe.web.request.OrderLineRequest
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class OrderServiceTest {

    private val orderRepository: OrderRepository = mockk()
    private val orderLineRepository: OrderLineRepository = mockk()
    private val orderService: OrderService = OrderService(orderRepository, orderLineRepository)

    @Test
    fun buildOrderLinesTest() {
        val order = Order(10000)
        val products = mutableListOf(Product(1, "상품1", Category.CAFFEINE, "", 1000, 100))
        val orderLines = mutableListOf(OrderLineRequest(1, 10))

        val buildOrderLines = orderService.buildOrderLines(order, products, orderLines)

        assertTrue(buildOrderLines.size == 1)
        assertTrue(buildOrderLines[0].product.saleCount == 10L)
        assertTrue(buildOrderLines[0].product.amount == 90L)
    }
}