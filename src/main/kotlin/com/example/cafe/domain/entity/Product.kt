package com.jyk.qrorder.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "product")
class Product(name: String, price: Long, amount: Long) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val productId: Long? = null   // 상품 PK

    @Column(name = "name")
    var name = name   // 상품명

    @Column(name = "price")
    var price = price   // 상품 가격

    @Column(name = "amount")
    var amount = amount   // 잔여 상품 개수
}