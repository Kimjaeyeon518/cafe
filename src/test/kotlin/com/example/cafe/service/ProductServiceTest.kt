package com.example.cafe.service

import com.example.cafe.domain.enums.Category
import com.example.cafe.repository.OrderLineRepository
import com.example.cafe.repository.OrderRepository
import com.example.cafe.repository.ProductRepository
import com.example.cafe.repository.UserRepository
import com.example.cafe.web.request.BuyRequest
import com.example.cafe.web.request.OrderLineRequest
import com.example.cafe.web.request.ProductCreateRequest
import com.example.cafe.web.request.SignUpRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors


@SpringBootTest
class ProductServiceTest(
    @Autowired
    val productService: ProductService,
    @Autowired
    val orderRepository: OrderRepository,
    @Autowired
    val orderLineRepository: OrderLineRepository,
    @Autowired
    val userService: UserService,
    @Autowired
    val productRepository: ProductRepository,
    @Autowired
    val userRepository: UserRepository
) {

    @BeforeEach
    fun init() {
        userService.signUp(SignUpRequest("jae518@naver.com", "wodus123", "재연", "USER", 1000000))
        userService.signUp(SignUpRequest("jae5181@naver.com", "wodus123", "재연", "USER", 1000000))
        productService.create(ProductCreateRequest("상품1", Category.CAFFEINE, "", 1000, 10000))
    }

    @Test
    fun concurrencyTest1() {
        val request = BuyRequest(listOf(OrderLineRequest(1, 1)))

        val numberOfThreads = 4
        val executor = Executors.newFixedThreadPool(numberOfThreads)
        val latch = CountDownLatch(4)

        executor.execute {
            productService.buy(1, request)
            latch.countDown()
        }
        executor.execute {
            productService.buy(2, request)
            latch.countDown()
        }
        executor.execute {
            productService.buy(1, request)
            latch.countDown()
        }
        executor.execute {
            productService.buy(2, request)
            latch.countDown()
        }
        latch.await()

        val product = productRepository.findById(1).orElseThrow()
        val user1 = userRepository.findById(1).orElseThrow()
        val user2 = userRepository.findById(2).orElseThrow()
        val order = orderRepository.findById(1).orElseThrow()
        val orderLines = orderLineRepository.findAll()

        assertThat(user1.point).isEqualTo(998000)
        assertThat(user2.point).isEqualTo(998000)
        assertThat(product.amount).isEqualTo(9996)
    }
}