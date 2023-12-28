package com.example.cafe.service

import com.example.cafe.domain.entity.User
import com.example.cafe.domain.enums.UserRole
import com.example.cafe.exception.BadRequestException
import com.example.cafe.repository.ProductRepository
import com.example.cafe.repository.UserRepository
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductServiceUnitTest {

    private val userRepository = mockk<UserRepository>()
    private val productRepository = mockk<ProductRepository>()
    private val orderService = mockk<OrderService>()
    private val productService = ProductService(userRepository, productRepository, orderService)

    @Test
    fun 사용자_잔여_포인트_차감() {
        val user = User("jae5181@naver.com", "wodus123", "재연", UserRole.ROLE_USER, 100000)
        user.deductPoint(10000)
        assertTrue(user.point == 90000L)
    }

    @Test
    fun 오류_사용자_잔여_포인트보다_큰_차감() {
        val user = User("jae5181@naver.com", "wodus123", "재연", UserRole.ROLE_USER, 100000)
        assertThrows<BadRequestException>(message = "포인트가 부족합니다.") {
            user.deductPoint(1000000)
        }
    }
}