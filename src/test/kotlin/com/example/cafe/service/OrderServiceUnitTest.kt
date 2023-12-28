package com.example.cafe.service

import com.example.cafe.domain.entity.Order
import com.example.cafe.domain.entity.OrderLine
import com.example.cafe.domain.entity.Product
import com.example.cafe.domain.enums.Category
import com.example.cafe.exception.BadRequestException
import com.example.cafe.exception.NotFoundException
import com.example.cafe.repository.OrderLineRepository
import com.example.cafe.repository.OrderRepository
import com.example.cafe.web.request.OrderLineRequest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class OrderServiceUnitTest {

    private val orderRepository: OrderRepository = mockk()
    private val orderLineRepository: OrderLineRepository = mockk()
    private val orderService: OrderService = OrderService(orderRepository, orderLineRepository)

    @Test
    fun 주문상세리스트_생성() {
        val order = Order(10000)
        val products = mutableListOf(Product(1, "상품1", Category.CAFFEINE, "", 1000, 100))
        val orderLines = mutableListOf(OrderLineRequest(1, 10))

        val buildOrderLines = orderService.buildOrderLines(order, products, orderLines)

        assertTrue(buildOrderLines.size == 1)
        assertTrue(buildOrderLines[0].product.saleCount == 10L)
        assertTrue(buildOrderLines[0].product.amount == 90L)
    }

    @Test
    fun 오류_유효하지_않은_상품_주문() {
        val order = Order(10000)
        val products = mutableListOf(Product(1, "상품1", Category.CAFFEINE, "", 1000, 100))
        val orderLines = mutableListOf(
            OrderLineRequest(1, 10),
            OrderLineRequest(2, 10)
        )

        assertThrows<NotFoundException>(message = "유효하지 않은 상품이 존재합니다.") {
            orderService.buildOrderLines(order, products, orderLines)
        }
    }

    @Test
    fun 오류_상품_재고를_초과하는_주문() {
        val order = Order(10000)
        val products = mutableListOf(Product(1, "상품1", Category.CAFFEINE, "", 1000, 100))
        val orderLines = mutableListOf(
            OrderLineRequest(1, 1000)
        )

        assertThrows<BadRequestException>(message = "상품 재고가 부족합니다.") {
            orderService.buildOrderLines(order, products, orderLines)
        }
    }

    @ParameterizedTest
//    @CsvSource(
//        "1, 3, 6, 9",
//        "2, 5, 6, 7",
//        "30, 50, 60, 70"
//    )
    @MethodSource("amounts")
    fun 인기상품_탑쓰리_조회(
        amount1: Long, amount2: Long, amount3: Long, amount4: Long
    ) {
        //given
        val order = Order(10000)
        val product1 = Product(1, "상품1", Category.CAFFEINE, "", 1000, 10)
        val product2 = Product(2, "상품2", Category.CAFFEINE, "", 1000, 10)
        val product3 = Product(3, "상품3", Category.CAFFEINE, "", 1000, 10)
        val product4 = Product(4, "상품4", Category.CAFFEINE, "", 1000, 10)
        val orderLines = listOf(
            OrderLine(order, product1, amount1),
            OrderLine(order, product2, amount2),
            OrderLine(order, product3, amount3),
            OrderLine(order, product4, amount4)
        )
        every { orderRepository.findByCreatedAtIsAfter(any()) } returns listOf(order)

        //when
        val result = orderService.findHit3Products()

        //then
        verify(exactly = 1) { orderRepository.findByCreatedAtIsAfter(any()) }
        assertTrue(result.size == 3)
        assertTrue(result[0].first == 4L)
        assertTrue(result[0].second == amount4)
        assertTrue(result[1].first == 3L)
        assertTrue(result[1].second == amount3)
        assertTrue(result[2].first == 2L)
        assertTrue(result[2].second == amount2)
    }

    companion object {
        @JvmStatic
        fun amounts() = listOf(
            Arguments.of(1, 3, 6, 9),
            Arguments.of(2, 5, 6, 7),
            Arguments.of(30, 50, 60, 70),
        )
    }
}