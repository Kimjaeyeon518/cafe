package com.example.cafe.integrationtest.service

import com.example.cafe.integrationtest.IntegrationTest
import com.example.cafe.repository.ProductRepository
import com.example.cafe.repository.UserRepository
import com.example.cafe.service.ProductService
import com.example.cafe.web.request.BuyRequest
import com.example.cafe.web.request.OrderLineRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class ProductServiceTest: IntegrationTest() {
    @Autowired
    lateinit var productService: ProductService
    @Autowired
    lateinit var productRepository: ProductRepository
    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun concurrencyTest1() {
        val request = BuyRequest(
            listOf(OrderLineRequest(1, 1)),
            1000
        )

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

        assertThat(user1.point).isEqualTo(998000)
        assertThat(user2.point).isEqualTo(998000)
        assertThat(product.amount).isEqualTo(9996)
    }
//
//    @Test
//    fun concurrencyTest2() {
//        val request = BuyRequest(
//            listOf(OrderLineRequest(1, 1)),
//            1000
//        )
//
//        val numberOfThreads = 4
//        val executor = Executors.newFixedThreadPool(numberOfThreads)
//        val latch = CountDownLatch(4)
//
//        executor.execute {
//            productService.buy(1, request)
//            latch.countDown()
//        }
//        executor.execute {
//            productService.buy(2, request)
//            latch.countDown()
//        }
//        executor.execute {
//            productService.buy(1, request)
//            latch.countDown()
//        }
//        executor.execute {
//            productService.buy(2, request)
//            latch.countDown()
//        }
//        latch.await()
//
//        val product = productRepository.findById(1).orElseThrow()
//        val user1 = userRepository.findById(1).orElseThrow()
//        val user2 = userRepository.findById(2).orElseThrow()
//
//        assertThat(user1.point).isEqualTo(998000)
//        assertThat(user2.point).isEqualTo(998000)
//        assertThat(product.amount).isEqualTo(9996)
//    }
}