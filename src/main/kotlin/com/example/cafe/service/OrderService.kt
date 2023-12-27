package com.example.cafe.service

import com.example.cafe.domain.entity.Product
import com.example.cafe.domain.enums.Category
import com.example.cafe.exception.NotFoundException
import com.example.cafe.repository.ProductRepository
import com.example.cafe.repository.UserRepository
import com.example.cafe.web.request.ProductBuyRequest
import com.example.cafe.web.request.ProductCreateRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    val userRepository: UserRepository,
    val productRepository: ProductRepository
) {

    @Transactional
    fun buy(userId: Long, request: ProductBuyRequest) {
        val user = userRepository.findByIdWithPessimisticLock(userId)
            ?: throw NotFoundException("NOT FOUND USER")

        val product = productRepository.findByIdWithPessimisticLock(request.productId)
            ?: throw NotFoundException("NOT FOUND PRODUCT")

        user.deductPoint(request.amount * product.price)
        product.purchase(request.amount)
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
        return productRepository.findTop3ByOrderBySaleCount()
    }
}