package com.jyk.qrorder.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "order_line")
class OrderLine(order: Order, productName: String, productAmount: Long, productPrice: Long) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderLineId: Long? = null

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "order_id", nullable = false)
    var order = order

    @Column(name = "product_name")
    var productName = productName

    @Column(name = "product_amount")
    var productAmount = productAmount

    @Column(name = "product_price")
    var productPrice = productPrice
}