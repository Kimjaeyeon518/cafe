package com.example.cafe.domain.entity

import com.example.cafe.domain.enums.Category
import com.example.cafe.exception.BadRequestException
import jakarta.persistence.*

@Entity
@Table(name = "product")
class Product(productId: Long? = null, name: String, category: Category, description: String, price: Long, amount: Long) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val productId: Long? = productId   // 상품 PK

    @Column(name = "name")
    var name = name   // 상품명

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    var category = category   // 상품 카테고리

    @Column(name = "description")
    var description = description   // 상품 설명

    @Column(name = "price")
    var price = price   // 상품 가격

    @Column(name = "amount")
    var amount = amount   // 잔여 상품 개수

    @Column(name = "sale_count")
    var saleCount: Long = 0  // 판매 개수

    fun purchased(amount: Long) {
        if (this.amount < amount) {
            throw BadRequestException("상품 재고가 부족합니다.")
        }
        this.amount -= amount
        this.saleCount += amount
    }
}