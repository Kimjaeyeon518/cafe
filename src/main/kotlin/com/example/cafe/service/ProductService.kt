package com.example.cafe.service

import com.example.cafe.domain.dto.OrderLineDto
import com.example.cafe.domain.entity.OrderLine
import com.example.cafe.domain.entity.Product
import com.example.cafe.domain.enums.Category
import com.example.cafe.exception.BadRequestException
import com.example.cafe.exception.NotFoundException
import com.example.cafe.repository.ProductRepository
import com.example.cafe.repository.UserRepository
import com.example.cafe.web.request.BuyRequest
import com.example.cafe.web.request.ProductCreateRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    val userRepository: UserRepository,
    val productRepository: ProductRepository,
    val orderService: OrderService
) {

    @Transactional
    fun buy(userId: Long, request: BuyRequest) {
        val user = userRepository.findByIdWithPessimisticLock(userId)
            ?: throw NotFoundException("NOT FOUND USER")
        val orderLines = request.orderLines
        val productIds = orderLines.map { it.productId }.toList()
        val products = productRepository.findByIdInWithPessimisticLock(productIds)
        if (orderLines.size != products.size) {
            throw NotFoundException("유효하지 않은 상품이 존재합니다.")
        }

        var totalPoint: Long = 0
        var orderLineList = mutableListOf<OrderLineDto>()
        for (product in products) {
            for (orderLine in orderLines) {
                if (product.productId != orderLine.productId) {
                    continue
                }

                if (product.amount < orderLine.amount) {
                    throw BadRequestException("${product.name} 상품의 재고가 부족합니다.")
                }
                product.purchase(orderLine.amount)
                totalPoint += product.price * orderLine.amount
                orderLineList.add(OrderLineDto(product, orderLine.amount))
            }
        }

        user.deductPoint(totalPoint)
        orderService.create(orderLineList, totalPoint)
    }

    @Transactional
    fun create(request: ProductCreateRequest): Product {
        val product = Product(
            name = request.name,
            category = request.category,
            description = request.description,
            price = request.price,
            amount = request.amount
        )
        return productRepository.save(product)
    }

    fun findByCategory(category: Category): List<Product> {
        return productRepository.findByCategory(category)
    }

    fun findHit(): List<Product> {
        val pairs = orderService.findHit3Products()
        return pairs.map {
            productRepository.findById(it.first).orElseThrow()
        }.toList()
    }
}